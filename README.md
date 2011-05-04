Kyoto Cabinet for Clojure
=========================

Kyoto Cabinet client implementation for clojure. It is a port of [brool's Tokyo Cabinet client][1] to Kyoto Cabinet.

Installation
------------

TODO.

Basically you'll need the Kyoto Cabinet c++ library installed, as well as the Java
bindings.

Usage
-----

See examples/text.clj.

**Note:** That unlike Tokyo Cabinet, the file extension of the database file
is significant. The extension is how the "type" of databse is selected.

Refer to the [java documention][2] for more information.

Links
-----

* Kyoto Cabinet -- http://fallabs.com/kyotocabinet/

License
-------

Copyright (C) 2011 Casey Link <unnamedrambler@gmail.com>

Distributed under the Eclipse Public License, the same as Clojure.


[1]: https://github.com/brool/tokyo-cabinet "Tokyo Cabinet for Clojure"
[2]: http://fallabs.com/kyotocabinet/javadoc/kyotocabinet/DB.html#open(java.lang.String, int)
