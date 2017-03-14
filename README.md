# Gigue

[![Build Status](https://travis-ci.org/OmoikaneIO/gigue.svg?branch=master)](https://travis-ci.org/OmoikaneIO/gigue)

## What is this?

This project contains Scala/ScalaJS code for generating *canonical* JSONs via
[circe](https://github.com/circe/circe).

A canonical JSON simply has all of the keys in all of its objects in
alphabetical order.

## Why Would I Want To Put My JSON in Canonical Form?

JSON is easily the most ubiquitous serialization format.

One thing someone might want to do with serialized data is to cryptographically
sign it, using [ECDSA][1] or [Schnorr Signatures][2].

However, different platforms might be able to represent the same data structure
yet have different ways of serializing that data to JSONs.

For C structs, Plain old Java objects, or Scala case classes, the order of the
keys read when deserializing from a JSON object does not matter.

By using canonical JSONs as serialization targets, all of these platforms may
serialize and sign data in a platform agnostic way.

[1]: https://en.wikipedia.org/wiki/Elliptic_Curve_Digital_Signature_Algorithm
[2]: https://en.wikipedia.org/wiki/Schnorr_signature

## Requirements

To run this, you will need the following installed on your system:

   - sbt 0.13.13 (or greater)
   - JDK 1.8.0 (or greater)
   - NodeJS v6.10.0 (or greater)


## Testing

```bash
# sbt test
```

## Contributors

- Matthew Wampler-Doty
