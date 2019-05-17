module Types.Payroll.TotalPayment exposing (TotalPayment(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type TotalPayment
    = EmptyTotalPayment
    | TotalPayment String


decoder : Decoder TotalPayment
decoder =
    string |> andThen (\str -> succeed (TotalPayment str))


toString : TotalPayment -> String
toString totalPayment =
    case totalPayment of
        TotalPayment value ->
            value

        EmptyTotalPayment ->
            ""
