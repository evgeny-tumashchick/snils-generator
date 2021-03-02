package snils

import generator.RandomValuesGenerator
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(MockKExtension::class)
internal class SnilsGeneratorTest {

  @MockK
  private lateinit var generator: RandomValuesGenerator

  private lateinit var snilsGenerator: SnilsGenerator

  @BeforeEach()
  fun setup() {
    snilsGenerator = SnilsGenerator(generator)
  }

  @AfterEach
  fun cleanup() {
    clearAllMocks()
  }

  @ParameterizedTest(name = "{index} generate Snils random part {2}")
  @CsvSource(
    "121212121, 65, lower than 100",
    "123341311, 00, equals 100",
    "123341312, 00, equals 101",
    "989898989, 82, higher that 101",
    "988728181, 02, higher that 101 and adds 0 to check digit as it lower than 10",
  )
  fun `generate Snils random part lower than 100`(
    randomPart: String,
    expectedCheckDigit: String,
    caseDescription: String
  ) {
    every { generator.randomDigits(SnilsGenerator.snilsRandomPartLength) } returns randomPart
    val snils = snilsGenerator.generateSnils()
    val checkDigit = snils.takeLast(SnilsGenerator.snilsCheckDigitLength)
    verify(exactly = 1) { generator.randomDigits(SnilsGenerator.snilsRandomPartLength) }
    assertEquals(expectedCheckDigit, checkDigit, "Snils check digit not valid")
  }

  @Test
  fun `generateRandomSnilsPart try to generate new snils if not valid and throws exception if fail`() {
    every { generator.randomDigits(SnilsGenerator.snilsRandomPartLength) } returns "123999123"
    assertThrows(IllegalStateException::class.java) { snilsGenerator.generateSnils() }
    verify(exactly = 3) { generator.randomDigits(SnilsGenerator.snilsRandomPartLength) }
  }

  @Test
  fun generateRandomSnilsPart() {
    val expectedSnilsRandomPart = "123123123"
    every { generator.randomDigits(SnilsGenerator.snilsRandomPartLength) } returns expectedSnilsRandomPart
    assertEquals(expectedSnilsRandomPart, snilsGenerator.generateRandomSnilsPart())
    verify(exactly = 1) { generator.randomDigits(SnilsGenerator.snilsRandomPartLength) }
  }

  @Test
  fun calculateCheckDigit() {
    val expectedSnilsRandomPart = "121212121"
    val expectedSnilsCheckDigits = "65"
    assertEquals(expectedSnilsCheckDigits, snilsGenerator.calculateCheckDigit(expectedSnilsRandomPart))
  }
}