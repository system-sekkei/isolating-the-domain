module Types.Time.ClockTime exposing (ClockTime(..), diffInMinutes, isLeftAfter)


type ClockTime
    = ClockTime Hour Minute


type alias Hour =
    Int


type alias Minute =
    Int


isLeftAfter : ClockTime -> ClockTime -> Bool
isLeftAfter leftClock rightClock =
    let
        ( leftHour, leftMinute ) =
            case leftClock of
                ClockTime hour minute ->
                    ( hour, minute )

        ( rightHour, rightMinute ) =
            case rightClock of
                ClockTime hour minute ->
                    ( hour, minute )
    in
    if leftHour > rightHour then
        True

    else if leftHour < rightHour then
        False

    else if leftMinute > rightMinute then
        True

    else
        False


diffInMinutes : ClockTime -> ClockTime -> Int
diffInMinutes leftClock rightClock =
    let
        leftTotalMinute =
            case leftClock of
                ClockTime hour minute ->
                    hour * 60 + minute

        rightTotalMinute =
            case rightClock of
                ClockTime hour minute ->
                    hour * 60 + minute
    in
    abs (rightTotalMinute - leftTotalMinute)
