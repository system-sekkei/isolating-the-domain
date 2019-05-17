module Types.TIme.WorkDate exposing (WorkDate(..), toString)


type WorkDate
    = EmptyWordDate
    | WorkDate String


toString : WorkDate -> String
toString workDate =
    case workDate of
        WorkDate value ->
            value

        EmptyWordDate ->
            ""


next : WorkDate -> WorkDate
next current =
    Debug.todo "todo"


previous : WorkDate -> WorkDate
previous current =
    Debug.todo "todo"
