module Components.AppHtmlEvents exposing (onChange)

import Html exposing (Attribute)
import Html.Events
import Json.Decode


onChange : (String -> msg) -> Attribute msg
onChange handler =
    Html.Events.on "change" (Json.Decode.map handler Html.Events.targetValue)
