module Types.TIme.YearMonth exposing (YearMonth(..), next, previous, toString)


type YearMonth
    = EmptyYearMonth
    | YearMonth String


toString : YearMonth -> String
toString yearMonth =
    case yearMonth of
        YearMonth value ->
            value

        EmptyYearMonth ->
            ""


next : YearMonth -> YearMonth
next yearMonth =
    Debug.todo "todo"


previous : YearMonth -> YearMonth
previous yearMonth =
    Debug.todo "todo"
