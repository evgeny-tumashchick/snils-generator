package snils

import generator.RandomValuesGenerator

/**
 * A generator for creating valid SNILS (Russian social security numbers).
 *
 * @param generator An instance of [RandomValuesGenerator] used for generating random numeric parts of the SNILS.
 */
internal class SnilsGenerator(private val generator: RandomValuesGenerator) {

  companion object {
    const val SNILS_RANDOM_PART_LENGTH: Int = 9
    const val SNILS_CHECK_DIGIT_LENGTH: Int = 2
  }

  private val snilsGenerateRetry: Int = 3
  private val snilsValidationRegex: String = "([0-9])\\1{2}"

  /**
   * Generates a full SNILS, including a random part and a check digit.
   *
   * @return A valid SNILS as a [String].
   */
  fun generateSnils(): String {
    val randomSnilsPart = generateRandomSnilsPart()
    val checkDigit = calculateCheckDigit(randomSnilsPart)
    return "$randomSnilsPart$checkDigit"
  }

  /**
   * Generates the random numeric part of the SNILS.
   *
   * @return A [String] representing the random part of the SNILS.
   * @throws IllegalStateException if a valid random SNILS part cannot be generated after a set number of retries.
   */
  fun generateRandomSnilsPart(): String {
    var randomSnilsPart = ""
    for (i in 1..snilsGenerateRetry) {
      randomSnilsPart = generator.randomDigits(SNILS_RANDOM_PART_LENGTH)
      if (isSnilsGeneratedPartValid(randomSnilsPart)) break
    }
    if (!isSnilsGeneratedPartValid(randomSnilsPart)) {
      throw IllegalStateException("Fail to generate valid random SNILS part")
    }
    return randomSnilsPart
  }

  /**
   * Calculates the check digit for a given random SNILS part.
   *
   * @param generatedSnilsPart The random part of the SNILS as a [String].
   * @return A [String] representing the check digit.
   */
  fun calculateCheckDigit(generatedSnilsPart: String): String {
    var calculateSnilsDigits = 0
    generatedSnilsPart.forEachIndexed { index: Int, char: Char ->
      calculateSnilsDigits += Character.getNumericValue(char) * (SNILS_RANDOM_PART_LENGTH - index)
    }
    return convertSnilsCalculationToCheckDigits(calculateSnilsDigits)
  }

  /**
   * Validates the generated SNILS part against a regex pattern.
   *
   * @param generatedSnilsPart The random part of the SNILS as a [String].
   * @return `true` if the SNILS part is valid, `false` otherwise.
   */
  private fun isSnilsGeneratedPartValid(generatedSnilsPart: String): Boolean {
    val notValidSnilsRegex = snilsValidationRegex.toRegex()
    return !notValidSnilsRegex.containsMatchIn(generatedSnilsPart)
  }

  /**
   * Converts the calculated SNILS digits to the corresponding check digit.
   *
   * @param calculateSnilsDigits The sum of the SNILS digits multiplied by their respective weights.
   * @return A [String] representing the check digit.
   * @throws IllegalArgumentException if the check digit cannot be correctly generated.
   */
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
      SNILS_CHECK_DIGIT_LENGTH -> checkDigit
      1 -> "0$checkDigit"
      else -> throw IllegalArgumentException("SNILS generation failed")
    }
  }
}