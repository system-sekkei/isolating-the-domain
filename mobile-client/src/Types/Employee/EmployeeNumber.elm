module Types.Employee.EmployeeNumber exposing (EmployeeNumber(..), decoder, encode, parse, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Json.Encode


type EmployeeNumber
    = EmptyEmployeeNumber
    | EmployeeNumber Int


parse : String -> EmployeeNumber
parse string =
    String.toInt string
        |> Maybe.map EmployeeNumber
        |> Maybe.withDefault EmptyEmployeeNumber


decoder : Decoder EmployeeNumber
decoder =
    string |> andThen (\str -> succeed (parse str))


toString : EmployeeNumber -> String
toString employeeNumber =
    case employeeNumber of
        EmployeeNumber value ->
            String.fromInt value

        EmptyEmployeeNumber ->
            ""


encode : EmployeeNumber -> Json.Encode.Value
encode employeeNumber =
    Json.Encode.string (toString employeeNumber)
