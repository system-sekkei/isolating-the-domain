module Components.AppHtmlUtils exposing (FieldErrorMessage, IsValid, fieldErrorMessage, inputStyle, nextLine, onChange, space)

import Html exposing (Attribute, Html, br, p, text)
import Html.Attributes exposing (class)
import Html.Events
import Json.Decode



-- Events


onChange : (String -> msg) -> Attribute msg
onChange handler =
    Html.Events.on "change" (Json.Decode.map handler Html.Events.targetValue)



-- Adjustment


space : String
space =
    "\u{00A0}"


nextLine : Html msg
nextLine =
    br [] []



-- Form


type alias IsValid =
    Bool


inputStyle : IsValid -> Attribute msg
inputStyle isValid =
    if isValid then
        class "input"

    else
        class "input is-danger"


type alias FieldErrorMessage =
    String


fieldErrorMessage : FieldErrorMessage -> Html msg
fieldErrorMessage err =
    if String.isEmpty err then
        text ""

    else
        p [ class "help is-danger" ] [ text err ]
