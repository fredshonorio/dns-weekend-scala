package pt.fredh.dnsweekendscala

import cats.effect.{IO, IOApp}
import cats.syntax.all._
import scodec.{Attempt, Codec, Decoder, Encoder}
import scodec.bits.ByteVector
import scodec.bits._
import scodec.codecs._

case class DNSHeader(
  id: Int,
  flags: Int,
  numQuestions: Int = 0,
  numAnswers: Int = 0,
  numAuthorities: Int = 0,
  numAdditionals: Int = 0
)

object DNSHeader {
  implicit val dnsHeaderCodec: Codec[DNSHeader] = (int16 :: int16 :: int16 :: int16 :: int16 :: int16).as[DNSHeader]
}

case class DNSQuestion(
  name: String,
  typ: Int,
  clss: Int
)

object DNSQuestion {

  /*
  encoded = b""
  for part in domain_name.encode("ascii").split(b"."):
      encoded += bytes([len(part)]) + part
  return encoded + b"\x00"
   */



  private val nameEncoder: Codec[String] =
    variableSizeBytes(int16, ascii) .f

  implicit val dnsQuestionEncoder: Codec[DNSQuestion] =
    (Codec(nameEncoder, ascii.asDecoder) :: int16 :: int16).as[DNSQuestion]
}

case class Query(
  header: DNSHeader,
  question: DNSQuestion
)

object Query {
  implicit val queryEncoder = (Codec[DNSHeader] :: Codec[DNSQuestion]).as[Query]
}

object Main extends IOApp.Simple {

  def run: IO[Unit] =
    IO.println(
      Codec[Query]
        .encode(
          Query(
            DNSHeader(0x1314, 0, 1),
            DNSQuestion("example.com", 1, 1)
          )
        )
        .getOrElse(BitVector.empty)
        .toHexDumpColorized
    )
}
