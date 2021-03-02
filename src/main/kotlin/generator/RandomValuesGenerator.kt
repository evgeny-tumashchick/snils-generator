package generator

class RandomValuesGenerator {

  fun randomDigits(digitsLength: Int): String {
    var generatedDigits = ""
    val rangeList = { (0..9).random() }
    while (generatedDigits.length < digitsLength) {
      generatedDigits += rangeList().toString()
    }
    return generatedDigits
  }
}