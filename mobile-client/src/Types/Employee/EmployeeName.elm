module Types.Employee.EmployeeName exposing (EmployeeName(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type EmployeeName
    = EmptyEmployeeName
    | EmployeeName String


decoder : Decoder EmployeeName
decoder =
    string |> andThen (\str -> succeed (EmployeeName str))


toString : EmployeeName -> String
toString employeeName =
    case employeeName of
        EmployeeName value ->
            value

        EmptyEmployeeName ->
            ""
