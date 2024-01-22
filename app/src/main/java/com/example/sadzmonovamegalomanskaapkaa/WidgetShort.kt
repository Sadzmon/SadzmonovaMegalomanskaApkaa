@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.sadzmonovamegalomanskaapkaa

import DataStoreManager
import android.util.Log
import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import com.example.sadzmonovamegalomanskaapkaa.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WidgetShort(
    selected: Boolean,
    icon: Painter,
    name: String,
    backgroundColor: Color =
        if (selected)
        {
            MaterialTheme.colorScheme.primary
        }
        else
        {
            MaterialTheme.colorScheme.secondary
        },
    modifier: Modifier = Modifier
) {
    //context
    val context = LocalContext.current
    // a coroutine scope
    val scope = rememberCoroutineScope()
    // we instantiate the dataStore class
    val dataStore = DataStoreManager(context)

    var offsetNow by remember { mutableStateOf(0f) }
    var offsetPrev by remember { mutableStateOf(0f) }
    var countOfDevices by remember { mutableStateOf(420) }

    var save by remember { mutableStateOf(false) }
    save = dataStore.getSaveState()
    Log.e("Save:", "$save")

    //Read the values from datastore only when composable occurs.
    if (countOfDevices == 420) {
        when (name) {
            "Alarm" -> {
                countOfDevices = dataStore.getAlarmCount()
                Log.d("Reading alarm count: ", "$countOfDevices")
            }

            "Kettle" -> {
                countOfDevices = dataStore.getKettleCount();
            }

            "Doorbell" -> {
                countOfDevices = dataStore.getDoorbellCount()
            }

            "Fridge" -> {
                countOfDevices = dataStore.getFridgeCount()
            }

            else -> {
                Text(text = "Error in name in WidgetShort function.")
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 8.dp, bottom = 16.dp, end = 16.dp)
            .background(
                backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
    )
    {
        //Pictogram of module
        Image(
            painter = icon,
            contentDescription = "Pictogram of $name",
            modifier = modifier.then(
                if (name == "Fridge") {
                    Modifier.padding(start = 24.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
                } else {
                    Modifier.padding(16.dp)
                }
            )
        )
        //Module name
        Text(
            text = name,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.scrim,
            modifier = modifier.padding(end = 16.dp)
        )
        Box(
            Modifier
                .size(40.dp)
                .scrollable(
                    orientation = Orientation.Vertical,
                    // Scrollable state: describes how to consume
                    // scrolling delta and update offset
                    state = rememberScrollableState { delta ->
                        offsetNow += delta
                        delta
                    }
                )
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            scope.launch {
                //Increase or decrease devices count and changing
                // the number of activated devices on WidgetShort
                if (offsetNow - offsetPrev > 70 && countOfDevices >= 1) {
                    countOfDevices = countOfDevices - 1
                    offsetPrev = offsetNow
                    offsetNow = 0f
                    offsetPrev = 0f
                    delay(100)
                } else if (offsetNow - offsetPrev < -70 && countOfDevices <= 3) {
                    countOfDevices = countOfDevices + 1
                    offsetPrev = offsetNow
                    offsetNow = 0f
                    offsetPrev = 0f
                    delay(100)
                }
                //Resetting offset to 0 or user can go outside of the range
                if (offsetNow > 280 || offsetNow < -280) {
                    offsetNow = 0f
                    offsetPrev = 0f
                }
            }
            Text(text = "$countOfDevices")
        }
        Log.i("Save:", "$save")

        if (save == true) {
            Log.e("Widget short", "Am I here???")
            Log.w("Count of devices?", "$countOfDevices")
            when (name) {
                "Alarm" -> {
                    scope.launch {
                        dataStore.saveAlarmCount( countOfDevices )
                    }
                    Log.d("Saving Alarm: ", "$countOfDevices")
                }

                "Kettle" -> {
                    scope.launch {
                        dataStore.saveKettleCount(countOfDevices)
                    }
                    //Log.w("Saving Kettle: ", "$countOfDevices")
                }

                "Doorbell" -> {
                    scope.launch {
                        dataStore.saveDoorbellCount(countOfDevices)
                    }
                    //Log.e("Saving Doorbell: ", "$countOfDevices")
                }

                "Fridge" -> {
                    scope.launch {
                        dataStore.saveFridgeCount(countOfDevices)
                    }
                    //Log.i("Saving Fridge: ", "$countOfDevices")
                }

                else -> {
                    Log.e("WidgetShort ", "Wrong name for saving counts.")
                }
            }
            scope.launch {
                dataStore.saveSaveState(false)
            }
        }
    }
        Log.i("??? ", "Do ted dobrý")

        //Assigning correct getFunctions to names
        /*when ( name )
        {
            "Alarm" ->
            {
                scope.launch {
                    dataStore.saveAlarmCount( countOfDevices )
                    delay(100)
                }
                Log.d("Saving Alarm: ", "$countOfDevices")
            }
            "Kettle" ->
            {
                scope.launch {
                    dataStore.saveKettleCount( countOfDevices )
                    delay(100)
                }
                //Log.w("Saving Kettle: ", "$countOfDevices")
            }
            "Doorbell" ->
            {
                scope.launch {
                    dataStore.saveDoorbellCount( countOfDevices )
                    delay(100)
                }
                //Log.e("Saving Doorbell: ", "$countOfDevices")
            }
            "Fridge" ->
            {
                scope.launch {
                    dataStore.saveFridgeCount( countOfDevices )
                    delay(100)
                }
                //Log.i("Saving Fridge: ", "$countOfDevices")
            }

            else -> {
                Log.e("WidgetShort ", "Wrong name for saving counts.")
                }
            }
    }*/
        Log.d("Widget short", "")

        Column {
            val infoA = dataStore.getAlarmCount()
            val infoK = dataStore.getKettleCount()
            val infoD = dataStore.getDoorbellCount()
            val infoF = dataStore.getFridgeCount()

            Row(Modifier.padding(top = 100.dp)) {
                Text(text = "Offset now = $offsetNow")
                Text(text = "Offset prev = $offsetPrev")
            }
            Text(text = "Alarm count = $infoA")
            Text(text = "Kettle count = $infoK")
            Text(text = "Doorbell count = $infoD")
            Text(text = "Fridge count = $infoF")

    }
}
