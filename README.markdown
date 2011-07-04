# Java Builder Pattern
Examples for Builder Pattern implementations in Java which initialize required and optional fields
in the same way. The original Block pattern in Effective Java is using a constructor for the required
fields which might be OK if you have only 2 required fields but not when there are more.

Foo demonstrates an implementation using generics, Bar uses an implementation which forces the user to
follow a determined sequence of initialzation steps.

## Example usage:
	Foo foo = new Foo(Foo.newBuilder().initR1(1).initR2(2).withO2(2));

	Bar bar = Bar.newBuilder().initR1(1).initR2(2).build();



