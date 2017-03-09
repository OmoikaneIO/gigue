package io.omoikane.gigue

import io.circe.Json.{Null, fromBoolean, fromJsonNumber, fromJsonObject, fromString, fromValues, fromFields}
import io.circe._

object CanonicalJson {

  /**
    * Put a Json into canonical form by recursively sorting the keys in all of its objects
    *
    * @param json A Json to be put in canonical form.
    * @return A Json with all keys in all objects sorted.
    */
  def sortJsonKeys(json: Json): Json =
    json.fold[Json](
      Null,
      fromBoolean,
      fromJsonNumber,
      fromString,
      (jsons: Vector[Json]) => fromValues(jsons.map(sortJsonKeys)),
      (jsonObject: JsonObject) =>
        fromFields(
            jsonObject.toVector
              .sortBy(_._1)
              .map({ case (k, v) => k -> sortJsonKeys(v) })
          )
    )

  /**
    * Convert an object to a Json in canonical form.
    *
    * @param data An object to convert
    * @param jsonEncoder Encoder for an object to circe Json
    * @tparam T The type of the object to convert
    * @return A Json representing the input in canonical form
    */
  def encodeCanonicalJson[T](data: T)(implicit jsonEncoder: Encoder[T]): Json = sortJsonKeys(jsonEncoder(data))

}
