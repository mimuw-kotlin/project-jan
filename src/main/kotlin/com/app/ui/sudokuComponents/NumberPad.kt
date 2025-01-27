import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

// Number pad that enables user inserting numbers into the board
@Composable
fun NumberPad(
    onNumberClick: (Int) -> Unit,
    isEditingNotes: Boolean,
) {
    Column(
        modifier =
            Modifier
                .padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        (1..9).chunked(3).forEach { rowNumbers ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                rowNumbers.forEach { number ->
                    Button(
                        onClick = { onNumberClick(number) },
                        colors =
                            ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFC5705D),
                                contentColor = Color.White,
                            ),
                        modifier = Modifier.testTag("number $number"),
                    ) {
                        Text(number.toString())
                    }
                }
            }
        }
        // Additional feature: clearing the cell
        Button(
            onClick = { onNumberClick(0) },
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFC5705D),
                    contentColor = Color.White,
                ),
        ) {
            Text("Clear Cell")
        }
        // Additional feature: adding notes to a cell
        Button(
            onClick = { onNumberClick(-1) },
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFC5705D),
                    contentColor = Color.White,
                ),
            modifier = Modifier.testTag("notesButton"),
        ) {
            Text(if (!isEditingNotes) "Notes: inactive" else "Notes: active")
        }
    }
}
