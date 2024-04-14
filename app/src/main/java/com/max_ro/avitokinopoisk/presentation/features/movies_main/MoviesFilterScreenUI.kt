package com.max_ro.avitokinopoisk.presentation.features.movies_main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.max_ro.avitokinopoisk.R
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.ConstantFeatureMain
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.MainScreenViewEvent
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.MovieFilterStateViewState
import com.max_ro.avitokinopoisk.presentation.features.movies_main.models.TypeOfContent


@Composable
fun FilterScreen(
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit
) {

    val context = LocalContext.current
    val listOfCountries = context.resources.getStringArray(R.array.countries_array).toList()
    val listOfGenres = context.resources.getStringArray(R.array.genres_array).toList()
    val listOfNetworks = context.resources.getStringArray(R.array.network_array).toList()

    LaunchedEffect(key1 = viewState) {
        onEvent(MainScreenViewEvent.IsFiltered(viewState))
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        SegmentedButtonSingleSelect(
            ConstantFeatureMain.buttonOptions,
            viewState,
            { onEvent(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 4.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Text(
            text = stringResource(R.string.input_year),
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 4.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            YearInputFieldFrom(
                minYear = viewState.yearFrom,
                maxYear = viewState.yearTo,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 5.dp),
                viewState = viewState,
                onEvent = { onEvent(it) },
            )
            YearInputFieldTo(
                minYear = viewState.yearFrom,
                maxYear = viewState.yearTo,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 15.dp),
                viewState = viewState,
                onEvent = { onEvent(it) },
            )
        }
        Text(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            color = Color.Red,
            fontSize = 11.sp,
            text = if (viewState.isYearInputError) {
                stringResource(R.string.year_in_diapazon, viewState.yearFrom, viewState.yearTo)
            } else {
                stringResource(R.string.Empty)
            }
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        Text(
            text = stringResource(R.string.choose_country),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
        )

        DynamicSelectTextField(
            selectedValue = stringResource(R.string.Empty),
            suggestions = listOfCountries,
            label = stringResource(R.string.production_country),
            errorLabel = stringResource(R.string.only_letters),
            onValueChangedEvent = { onEvent(MainScreenViewEvent.SaveCountries(it)) },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 4.dp),
            type = TypeOfContent.StringContent,
            checkFunction = ::isOnlyLetters
        )
        ChipsListCountries(
            viewState, { onEvent(it) }, modifier = Modifier.padding(horizontal = 15.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Text(
            text = stringResource(R.string.choose_genre),
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 4.dp),
        )
        DynamicSelectTextField(
            selectedValue = stringResource(R.string.Empty),
            suggestions = listOfGenres,
            label = stringResource(R.string.genre),
            errorLabel = stringResource(R.string.only_letters),
            onValueChangedEvent = { onEvent(MainScreenViewEvent.SaveGenres(it)) },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 4.dp),
            type = TypeOfContent.StringContent,
            checkFunction = ::isOnlyLetters
        )
        ChipsListGenres(
            viewState, { onEvent(it) }, modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        RatingSlider(
            viewState = viewState,
            onEvent = { onEvent(it) },
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        Text(
            text = stringResource(R.string.production_networks),
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 4.dp),
        )
        DynamicSelectTextField(
            selectedValue = stringResource(R.string.Empty),
            suggestions = listOfNetworks,
            label = stringResource(R.string.networks),
            errorLabel = stringResource(R.string.only_letters),
            onValueChangedEvent = { onEvent(MainScreenViewEvent.SaveNetworks(it)) },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 4.dp),
            type = TypeOfContent.StringContent,
            checkFunction = { true }
        )
        ChipsListNetworks(
            viewState, { onEvent(it) }, modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        DiscreteAgeSlider(
            discreteValues = ConstantFeatureMain.ageRatingDiscreteValues,
            viewState = viewState,
            onEvent = { onEvent(it) },
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
        )
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
            thickness = 1.dp,
            color = Color.Transparent
        )
    }
}

@Composable
fun YearInputFieldFrom(
    minYear: Int,
    maxYear: Int,
    modifier: Modifier,
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
) {
    var text by remember { mutableStateOf(viewState.yearFrom.toString()) }
    var isValid by remember { mutableStateOf(false) }

    LaunchedEffect(viewState.isFiltered) {
        text = viewState.yearFrom.toString()
    }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            val newTextIsValid = it.length == 4 && it.toIntOrNull()?.let { year ->
                year in minYear..maxYear
            } == true
            isValid = newTextIsValid

            if (it.isEmpty() || (it.length <= 4 && it.all { char -> char.isDigit() })) {
                text = it
                onEvent(MainScreenViewEvent.IsInputError(text.isNotEmpty() && !isValid))
            }
        },
        label = {
            Text(
                text = stringResource(R.string.from)
            )
        },
        singleLine = true,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .clickable {
                        onEvent(MainScreenViewEvent.SaveYearFrom(ConstantFeatureMain.minYear))
                        text = ConstantFeatureMain.minYear.toString()
                        isValid = true
                    },
                imageVector = Icons.Default.Close,
                contentDescription = "Search Icon"
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isValid) Color(0xFF8FBC8F) else Color.Blue,
            unfocusedBorderColor = if (isValid) Color(0xFF8FBC8F) else Color.Gray
        )
    )
    if (text.isNotEmpty() && isValid) {
        onEvent(MainScreenViewEvent.SaveYearFrom(text.toInt()))
    }
}

@Composable
fun YearInputFieldTo(
    minYear: Int,
    maxYear: Int,
    modifier: Modifier,
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
) {
    var text by remember { mutableStateOf(viewState.yearTo.toString()) }
    var isValid by remember { mutableStateOf(false) }
    LaunchedEffect(viewState.isFiltered) {
        text = viewState.yearTo.toString()
    }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            val newTextIsValid = it.length == 4 && it.toIntOrNull()?.let { year ->
                year in minYear..maxYear
            } == true
            isValid = newTextIsValid
            if (it.isEmpty() || (it.length <= 4 && it.all { char -> char.isDigit() })) {
                text = it
                onEvent(MainScreenViewEvent.IsInputError(text.isNotEmpty() && !isValid))
            }
        },
        label = {
            Text(
                text =
                stringResource(R.string.until)
            )
        },
        singleLine = true,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .clickable {
                        onEvent(MainScreenViewEvent.SaveYearTo(ConstantFeatureMain.maxYear))
                        text = ConstantFeatureMain.maxYear.toString()
                        isValid = true
                    },
                imageVector = Icons.Default.Close,
                contentDescription = "Search Icon"
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isValid) Color(0xFF8FBC8F) else Color.Blue,
            unfocusedBorderColor = if (isValid) Color(0xFF8FBC8F) else Color.Gray
        )
    )
    if (text.isNotEmpty() && isValid) {

        onEvent(MainScreenViewEvent.SaveYearTo(text.toInt()))

    }
}

@Composable
fun DiscreteAgeSlider(
    discreteValues: List<Int>,
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
    modifier: Modifier,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.ageRating),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = displayAgeRating(Pair(viewState.ageRating.first, viewState.ageRating.second)),
            )
        }
        RangeSlider(
            value = viewState.ageRatingSlidePosition,
            onValueChange = { onEvent(MainScreenViewEvent.SaveAgeSlidePosition(it)) },
            valueRange = 0f..(discreteValues.size - 1).toFloat(),
            steps = discreteValues.size - 2,
            onValueChangeFinished = { }
        )
    }
}

fun displayAgeRating(ageRating: Pair<Int, Int>): String {

    return if (ageRating.first == 0 && ageRating.second == 18) {
        "  неважно"
    } else if (ageRating.second == 18) {
        "  ${ageRating.first} - ${ageRating.second}+"
    } else {
        "  ${ageRating.first} - ${ageRating.second}"
    }
}

@Composable
fun RatingSlider(
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
    modifier: Modifier,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.choose_rating),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = displayMovieRating(Pair(viewState.movieRating.first, viewState.movieRating.second)),
            )
        }
        RangeSlider(
            value = viewState.movieRatingSlidePosition,
            onValueChange =
            { onEvent(MainScreenViewEvent.SaveMovieRatingSlidePosition(it)) },
            valueRange = 0f..10f,
            steps = 9,
        )
    }
}

fun displayMovieRating(movieRating: Pair<Int, Int>): String {
    return if (movieRating.first == 0 && movieRating.second == 10) {
        "  неважно"
    } else {
        "от ${movieRating.first} до ${movieRating.second}"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtonSingleSelect(
    options: List<String>,
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
    modifier: Modifier,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = {
                    when (index) {
                        0 -> onEvent(MainScreenViewEvent.SaveIsSeries(""))
                        1 -> onEvent(MainScreenViewEvent.SaveIsSeries("false"))
                        2 -> onEvent(MainScreenViewEvent.SaveIsSeries("true"))
                    }
                },
                selected = index == viewState.isSeries.toSeriesIndex()
            ) {
                Text(label)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsListCountries(
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
    modifier: Modifier,
) {
    FlowRow(
        modifier = modifier,
    ) {
        viewState.countries?.forEach { item ->
            AssistChip(
                label = { Text(item) },
                onClick = {
                    onEvent(MainScreenViewEvent.DeleteCountries(item))
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "DeleteCountry",
                    )
                },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsListGenres(
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
    modifier: Modifier,
) {
    FlowRow(
        modifier = modifier,
    ) {
        viewState.genres.forEach { item ->
            AssistChip(
                label = { Text(item) },
                onClick = {
                    onEvent(MainScreenViewEvent.DeleteGenres(item))
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "DeleteGenres",
                    )
                },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsListNetworks(
    viewState: MovieFilterStateViewState,
    onEvent: (MainScreenViewEvent) -> Unit,
    modifier: Modifier,
) {

    FlowRow(
        modifier = modifier,
    ) {
        viewState.networks.forEach { item ->
            AssistChip(
                label = { Text(item) },
                onClick = {
                    onEvent(MainScreenViewEvent.DeleteNetworks(item))
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "DeleteNetwoks",
                    )
                },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String = "",
    suggestions: List<String> = listOf(),
    label: String = "",
    errorLabel: String = "",
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier,
    type: TypeOfContent,
    checkFunction: (String) -> Boolean,

    ) {
    var textFieldValue by remember { mutableStateOf(selectedValue) }
    var expanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager: FocusManager = LocalFocusManager.current
    val isError = remember(textFieldValue) { !checkFunction(textFieldValue) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            label = {
                Text(
                    text =
                    if (isError) {
                        errorLabel
                    } else {
                        label
                    }
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),

            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (!isError && type is TypeOfContent.StringContent) {
                        onValueChangedEvent(textFieldValue)
                        textFieldValue = ""
                        expanded = false
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                },
            ),
            isError = isError
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            suggestions.filter { it.startsWith(textFieldValue, ignoreCase = true) }.forEach { suggestion ->

                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    text = { Text(text = suggestion) },
                    onClick = {
                        if (!isError) {
                            if (type is TypeOfContent.IntContent) {
                                textFieldValue = suggestion
                            }
                            focusManager.clearFocus()
                            expanded = false
                            onValueChangedEvent(suggestion)
                            keyboardController?.hide()
                        }
                    }
                )
            }
        }
    }
}


fun isOnlyLetters(text: String): Boolean {
    return text.all { it.isLetter() }
}

fun isOnlyDigits(text: String): Boolean {
    return text.all { it.isDigit() }
}

fun String.toSeriesIndex(): Int {
    return when (this) {
        "false" -> 1
        "true" -> 2
        else -> 0
    }
}
