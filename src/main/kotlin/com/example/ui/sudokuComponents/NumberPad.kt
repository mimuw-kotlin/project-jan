import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                    Button(onClick = { onNumberClick(number) }) {
                        Text(number.toString())
                    }
                }
            }
        }
    }
}
