import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NumberPad(onNumberClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (1..9).chunked(3).forEach { rowNumbers ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowNumbers.forEach { number ->
                    Button(
                        onClick = { onNumberClick(number)},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFC5705D),
                            contentColor = Color.White
                        )) {
                        Text(number.toString())
                    }
                }
            }
        }
        Button(
            onClick = { onNumberClick(0)},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFC5705D),
                contentColor = Color.White
            )) {
            Text("Clear Cell")
        }
    }
}
