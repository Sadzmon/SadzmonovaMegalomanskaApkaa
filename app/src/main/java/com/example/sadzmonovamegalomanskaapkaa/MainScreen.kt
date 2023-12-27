package com.example.sadzmonovamegalomanskaapkaa

import DataStoreManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    var selectedAlarm    by rememberSaveable { mutableStateOf( false ) }
    var selectedKettle   by rememberSaveable { mutableStateOf( false ) }
    var selectedFridge   by rememberSaveable { mutableStateOf( false ) }
    var selectedDoorbell by rememberSaveable { mutableStateOf( false ) }

    var count = 0
    /*Column {
        for ( i in modules )
        {
            when ( modules[count] )
            {
                "Alarm" -> Widget(
                        icon = painterResource( id = R.drawable.alarm ),
                        name = "Alarm",
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        selected = selectedAlarm,
                        modifier = Modifier,
                        onClick = { selectedAlarm = !selectedAlarm }
                        )
                "Kettle" -> Widget(
                        icon = painterResource( id = R.drawable.kettle ),
                        name = "Kettle",
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        selected = selectedKettle,
                        modifier = Modifier,
                        onClick = { selectedKettle = !selectedKettle }
                        )
                "Fridge" -> Widget(
                        icon = painterResource( id = R.drawable.fridge ),
                        name = "Fridge",
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        selected = selectedFridge,
                        modifier = Modifier,
                        onClick = { selectedFridge = !selectedFridge }
                        )
                "Doorbell" -> Widget(
                        icon = painterResource( id = R.drawable.doorbell ),
                        name = "Doorbell",
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        selected = selectedDoorbell,
                        modifier = Modifier,
                        onClick = { selectedDoorbell = ! selectedDoorbell }
                        )
            }
            count = count + 1
        }
    }*/

}

@Composable
fun LoginSCreen() {
    //context
    val context = LocalContext.current
    // a coroutine scope
    val scope = rememberCoroutineScope()
    // we instantiate the saveEmail class
    val dataStore = DataStoreManager(context)

    var selectedAlarm_0    by rememberSaveable { mutableStateOf( false ) }
    var selectedKettle_0   by rememberSaveable { mutableStateOf( false ) }
    var selectedFridge_0   by rememberSaveable { mutableStateOf( false ) }
    var selectedDoorbell_0 by rememberSaveable { mutableStateOf( false ) }

    var alarm_0 = dataStore.getAlarm( index = 0 )

    Column(modifier = Modifier.wrapContentSize()) {
        if (alarm_0 == true) {
            Widget(
                icon = painterResource(id = R.drawable.alarm),
                name = "Alarm",
                backgroundColor = MaterialTheme.colorScheme.primary,
                selected = selectedAlarm_0,
                modifier = Modifier,
                onClick = { selectedAlarm_0 = !selectedAlarm_0 }
            )
        }

        var email by rememberSaveable { mutableStateOf("") }

        Text(
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .alpha(0.6f),
            text = "EMAIL",
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontSize = 12.sp
        )
        //email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },

            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType
                = KeyboardType.Email
            ),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .fillMaxWidth(),

            )
        Spacer(modifier = Modifier.height(16.dp) )

        Button(
            onClick = {
                //launch the class in a coroutine scope
                scope.launch {
                    dataStore.saveAlarm(0, email )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height( 60.dp )
                .padding( 16.dp, 0.dp, 16.dp, 0.dp ),
        ) {
            Text(
                color = Color.White,
                text = "Save Email"
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        val count = dataStore.getAlarmCount()

        Text(text = "Count: $count")

    }
}