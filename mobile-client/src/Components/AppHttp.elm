module Components.AppHttp exposing (Error(..), ServerErrorMessage, expectJson)

import Http exposing (Expect, expectStringResponse)
import Json.Decode exposing (string)
import Json.Decode.Pipeline


type Error
    = NoError
    | BadUrl String
    | Timeout
    | NetworkError
    | BadBody String
    | BadStatus Int
    | ApplicationError ServerErrorMessage


type alias ServerErrorMessage =
    { type_ : String
    , messages : List String
    }


serverErrorDecoder : Json.Decode.Decoder ServerErrorMessage
serverErrorDecoder =
    Json.Decode.succeed ServerErrorMessage
        |> Json.Decode.Pipeline.required "type" string
        |> Json.Decode.Pipeline.required "messages" (Json.Decode.list string)


expectJson : (Result Error a -> msg) -> Json.Decode.Decoder a -> Expect msg
expectJson toMsg decoder =
    expectStringResponse toMsg <|
        \response ->
            case response of
                Http.BadUrl_ url ->
                    Err (BadUrl url)

                Http.Timeout_ ->
                    Err Timeout

                Http.NetworkError_ ->
                    Err NetworkError

                Http.BadStatus_ metadata body ->
                    case Json.Decode.decodeString serverErrorDecoder body of
                        Ok error ->
                            Err (ApplicationError error)

                        Err _ ->
                            Err (BadStatus metadata.statusCode)

                Http.GoodStatus_ _ body ->
                    case Json.Decode.decodeString decoder body of
                        Ok value ->
                            Ok value

                        Err err ->
                            Err (BadBody (Json.Decode.errorToString err))
