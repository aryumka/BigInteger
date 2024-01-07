package aryumka.biginteger

class BigInteger(value: String) {
  val integer: String
  val sign: Sign

  init {
    if (value.isEmpty()) {
      throw IllegalArgumentException("value must not be empty")
    }
    if (!value.matches(Regex("[-|+]?[0-9]+"))) {
      throw IllegalArgumentException("value must be a number")
    }
    if (value.startsWith("-") || value.startsWith("+")) {
      this.integer = value.substring(1)
      this.sign = if (value.startsWith("-")) Sign.NEGATIVE else Sign.POSITIVE
    } else {
      this.integer = value
      this.sign = Sign.POSITIVE
    }
  }

  // Factory methods
  companion object {
    fun of(value: String): BigInteger {
      return BigInteger(value)
    }

    fun of(value: Int): BigInteger {
      return BigInteger(value.toString())
    }

    fun of(value: Long): BigInteger {
      return BigInteger(value.toString())
    }
  }

  // Plus
  private fun plus(other: String): BigInteger {
    var result = ""
    var carry = 0
    var valueLength = this.integer.length - 1
    var otherLength = other.length - 1

    while (valueLength >= 0 || otherLength >= 0 || carry > 0) {
      val valueDigit = if (valueLength >= 0) this.integer[valueLength] - '0' else 0
      val otherDigit = if (otherLength >= 0) other[otherLength] - '0' else 0

      val sum = valueDigit + otherDigit + carry

      result += (sum % 10)
      carry = sum / 10

      valueLength--
      otherLength--
    }

    return BigInteger(result.reversed())
  }

  operator fun plus(other: Int): BigInteger =
    this.plus(other.toString())

  operator fun plus(other: Long): BigInteger =
    this.plus(other.toString())

  operator fun plus(other: BigInteger): BigInteger =
    this.plus(other.integer)



  // Minus
  operator fun minus(other: Int): BigInteger =
    this.minus(other.toString())

  operator fun minus(other: Long): BigInteger =
    this.minus(other.toString())

  operator fun minus(other: BigInteger): BigInteger =
    this.minus(other.integer)

  private fun minus(other: String): BigInteger {
    var result = ""
    var carry = 0

    //find out which is bigger
    var minuend = ""
    var subtrahend = ""
    var sign = ""
    if (this.integer.length > other.length) {
      minuend = this.integer
      subtrahend = other
      sign = ""
    } else if (this.integer.length < other.length) {
      minuend = other
      subtrahend = this.integer
      sign = "-"
    } else {
      //same length
      if (this.integer > other) {
        minuend = this.integer
        subtrahend = other
        sign = ""
      } else {
        minuend = other
        subtrahend = this.integer
        sign = "-"
      }
    }

    var valueLength = minuend.length - 1
    var otherLength = subtrahend.length - 1
    while (valueLength >= 0 || otherLength >= 0 || carry > 0) {
      var valueDigit = if (valueLength >= 0) this.integer[valueLength] - '0' else 0
      val otherDigit = if (otherLength >= 0) other[otherLength] - '0' else 0

      if (valueDigit < otherDigit) {
        valueDigit += 10
      }

      val diff = valueDigit - otherDigit - carry

      if (valueDigit >= 10) {
        carry = 1
      } else {
        carry = 0
      }

      result += if (diff % 10 > 0) diff % 10 else ""

      valueLength--
      otherLength--
    }

    //todo : refactor this with negative method
    return BigInteger(result.reversed())
  }

  // Times
  operator fun times(other: Int): BigInteger =
    this.times(other.toString())

  operator fun times(other: Long): BigInteger =
    this.times(other.toString())

  operator fun times(other: BigInteger): BigInteger =
    this.times(other.toString())

  private fun times(other: String): BigInteger {
    var result = ""
    var carry = 0
    var valueLength = this.integer.length - 1
    var otherLength = other.length - 1

    while (valueLength >= 0 || otherLength >= 0 || carry > 0) {
      val valueDigit = if (valueLength >= 0) this.integer[valueLength] - '0' else 0
      val otherDigit = if (otherLength >= 0) other[otherLength] - '0' else 0

      val sum = valueDigit * otherDigit + carry

      result += (sum % 10)
      carry = sum / 10

      valueLength--
      otherLength--
    }

    return BigInteger(result.reversed())
  }
  // Div
  operator fun div(other: Int): BigInteger =
    TODO()

  operator fun div(other: Long): BigInteger =
    TODO()

  operator fun div(other: BigInteger): BigInteger =
    TODO()

  // Rem
  operator fun rem(other: Int): BigInteger =
    TODO()

  operator fun rem(other: Long): BigInteger =
    TODO()

  operator fun rem(other: BigInteger): BigInteger =
    TODO()

  // Unary
  operator fun unaryPlus(): BigInteger =
    TODO()

  operator fun unaryMinus(): BigInteger =
    TODO()

  // Not
  operator fun not(): BigInteger =
    TODO()

  override fun equals(other: Any?): Boolean =
    when (other) {
      is BigInteger -> this.toString() == other.toString()
      is String -> this.toString() == other
      is Int -> this.toString() == other.toString()
      is Long -> this.toString() == other.toString()
      else -> false
    }

  override fun toString(): String = if (this.sign == Sign.POSITIVE) this.integer else "-${this.integer}"

  enum class Sign {
    POSITIVE, NEGATIVE
  }
}
