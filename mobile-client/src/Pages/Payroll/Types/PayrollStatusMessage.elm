module Pages.Payroll.Types.PayrollStatusMessage exposing (PayrollStatusMessage(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type PayrollStatusMessage
    = EmptyPayrollStatusMessage
    | PayrollStatusMessage String


decoder : Decoder PayrollStatusMessage
decoder =
    string |> andThen (\str -> succeed (PayrollStatusMessage str))


toString : PayrollStatusMessage -> String
toString message =
    case message of
        PayrollStatusMessage value ->
            value

        EmptyPayrollStatusMessage ->
            ""
