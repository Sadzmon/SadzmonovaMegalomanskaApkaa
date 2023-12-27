package com.example.sadzmonovamegalomanskaapkaa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sadzmonovamegalomanskaapkaa.ui.theme.AppTheme

@OptIn( ExperimentalMaterial3Api::class )
@Composable
fun Widget(
    icon: Painter,
    name: String,
    backgroundColor: Color,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    val notEstablished = painterResource( id = R.drawable.not_established )
    val optionMenu     = painterResource( id = R.drawable.three_dots )

    Column (
        Modifier
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .clickable { onClick() }
        )
        {
            //Pictogram of module
            Image(
                painter            = icon,
                contentDescription = "Pictogram of $name",
                modifier = Modifier.then(
                    if ( name == "Fridge" ) {
                        Modifier.padding( start = 24.dp, top = 16.dp, bottom = 16.dp, end = 16.dp )
                    } else {
                        Modifier.padding( 16.dp )
                    } ),
            )
            //Module name
            Text(
                text     = name,
                fontSize = 28.sp,
                modifier = Modifier
            )

            Spacer(Modifier.weight(1f))

            //Pictogram informing about connection
            Image(
                painter = notEstablished,
                contentDescription = "Pictogram informing about connection"
            )

            //option menu
            Image(
                painter = optionMenu,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp, end = 24.dp)
            )
        }
        if ( name == "Alarm" ) {
            var hours          by remember { mutableStateOf("") }
            var minutes        by remember { mutableStateOf("") }
            var alarmingTime   by remember { mutableStateOf("") }
            var state         by rememberSaveable { mutableStateOf(false) }

            if ( selected  == true )
            {
                Column {
                    Row {
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp),
                            value = hours,
                            onValueChange = { hours = it },
                            label = { Text( text = "Hours" )
                            }
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp),
                            value = minutes,
                            onValueChange = { minutes = it},
                            label = { Text( text = "Minutes" ) }
                        )
                    }
                    Row {
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp),
                            value = alarmingTime,
                            onValueChange = { alarmingTime = it },
                            label = { Text( text = "Alarming time" )
                            }
                        )
                        ElevatedButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp)
                                .padding(8.dp),
                            onClick = {  }) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun previewwid()
{
    var selectedAlarm_0    by rememberSaveable { mutableStateOf( true ) }

    AppTheme {
        Widget(
            icon = painterResource(id = R.drawable.alarm),
            name = "Alarm",
            backgroundColor = MaterialTheme.colorScheme.primary,
            selected = selectedAlarm_0,
            modifier = Modifier,
            onClick = { selectedAlarm_0 = !selectedAlarm_0 }
        )

    }
}