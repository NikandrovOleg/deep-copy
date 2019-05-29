# Deep copy

Library for deep copy of objects on Kotlin. Primitive variables and enumerations will be used the same. Other objects and collections will be created.

## Requirements

Every object in graph of objects should have default constructor.

### Exceptions

IllegalArgumentException will be caused by inability to create object due to lack of default constructor in graph.

## License

MIT license - see the [LICENSE.md](LICENSE.md) file for details