package dashboard.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import dashboard.domain.model.Mentoring
import dashboard.presentation.utils.TextFormattingUtils

@Composable
fun TotalRevenueChart(
    mentories: List<Mentoring>,
    modifier: Modifier = Modifier
) {
    
    val data = remember(mentories) {
        buildChartDataFromMentoriesList(mentories)
    }

    val testLineParameters: List<LineParameters> = listOf(
        LineParameters(
            label = "Recaudo total",
            data = data.values.toList(),
            lineColor = Color.Green,
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            
        )
    )

    Box(modifier) {
        LineChart(
            modifier = Modifier.fillMaxWidth(),
            linesParameters = testLineParameters,
            isGrid = false,
            gridColor = MaterialTheme.colorScheme.onSurface,
            xAxisData = data.keys.toList(),
            animateChart = true,
            showGridWithSpacer = true,
            yAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
            ),
            xAxisStyle = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.W400
            ),
            yAxisRange = 14,
            oneLineChart = false,
        )
    }
}

private fun buildChartDataFromMentoriesList(mentories: List<Mentoring>): Map<String, Double> {
    val orderedMentories = mentories
        .sortedBy { TextFormattingUtils.convertToTimestamp(it.date) }
    val data = mutableMapOf<String, Double>()
    
    orderedMentories.forEach { mentoring ->
        data[mentoring.date] = data[mentoring.date]?.plus(mentoring.totalRevenue) ?: mentoring.totalRevenue 
    }
    
    return data
}
