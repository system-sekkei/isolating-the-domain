module Components.AppHtmlUtils exposing (nextLine, onChange, space)

import Html exposing (Attribute, Html, br)
import Html.Events
import Json.Decode



-- Events


onChange : (String -> msg) -> Attribute msg
onChange handler =
    Html.Events.on "change" (Json.Decode.map handler Html.Events.targetValue)



-- Utilities


space : String
space =
    "\u{00A0}"


nextLine : Html msg
nextLine =
    br [] []
