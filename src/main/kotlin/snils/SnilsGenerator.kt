package snils

import generator.RandomValuesGenerator

internal class SnilsGenerator(private val generator: RandomValuesGenerator) {

  companion object {
    const val snilsRandomPartLength: Int = 9
    const val snilsCheckDigitLength: Int = 2
  }

  private val snilsGenerateRetry: Int = 3
  private val snilsValidationRegex: String = "([0-9])\\1{2}"

  fun generateSnils(): String {
    val randomSnilsPart = generateRandomSnilsPart()
    val checkDigit = calculateCheckDigit(randomSnilsPart)
    return "$randomSnilsPart$checkDigit"
  }

  fun generateRandomSnilsPart(): String {
    var randomSnilsPart = ""
    for (i in 1..snilsGenerateRetry) {
      randomSnilsPart = generator.randomDigits(snilsRandomPartLength)
      if (isSnilsGeneratedPartValid(randomSnilsPart)) break
    }
    if (!isSnilsGeneratedPartValid(randomSnilsPart)) {
      throw IllegalStateException("Fail to generate valid random snils part")
    }
    return randomSnilsPart
  }

  fun calculateCheckDigit(generatedSnilsPart: String): String {
    var calculateSnilsDigits = 0
    generatedSnilsPart.forEachIndexed { index: Int, char: Char ->
      calculateSnilsDigits += Character.getNumericValue(char) * (snilsRandomPartLength - index)
    }
    return convertSnilsCalculationToCheckDigits(calculateSnilsDigits)
  }

  private fun isSnilsGeneratedPartValid(generatedSnilsPart: String): Boolean {
    val notValidSnilsRegex = snilsValidationRegex.toRegex()
    return !notValidSnilsRegex.containsMatchIn(generatedSnilsPart)
  }

  private fun convertSnilsCalculationToCheckDigits(calculateSnilsDigits: Int): String {
    val checkDigit = when {
      calculateSnilsDigits < 100 -> calculateSnilsDigits.toString()
      calculateSnilsDigits == 100 || calculateSnilsDigits == 101 -> "00"
      else -> {
        val recalculateSnilsDigits: Int = calculateSnilsDigits % 101
        convertSnilsCalculationToCheckDigits(recalculateSnilsDigits)
      }
    }
    return when (checkDigit.length) {
      snilsCheckDigitLength -> checkDigit
      1 -> "0$checkDigit"
      else -> throw IllegalArgumentException("Snils generation failed")
    }
  }
}