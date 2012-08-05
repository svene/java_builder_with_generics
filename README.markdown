# Java Builder Pattern
Examples for Builder Pattern implementations in Java which initialize required and optional fields
in the same way. The original pattern in Josh Bloch's Effective Java is using a constructor for the required
fields which might be OK if you have only 2 required fields but not when there are more.

'generics' demonstrates an implementation using generics, 'namedconstruction' uses an implementation which forces the user to
follow a determined sequence of initialzation steps.

At the moment my personal favorite pattern is 'namedconstruction'.


## Example usage of 'generics':
	new Foo(Foo.newBuilder().Required2(2).Required1(1));

	new Foo(Foo.newBuilder().Required1(1).Required2(2).optional1(3).optional2(4).optional3(5));



## Example usage of 'namedconstruction':
	NCFoo foo = NCFoo.required1("1").required2("2").required3("3").build();

	NCFoo foo = NCFoo.required1("1").required2("2").required3("3").optional1("o1").optional2("o2").optional3("o3").build();


## Example usage of 'typedconstruction':
	TCFoo foo = new TCFoo(required1("1"), required2(2), required3(3), optional1("o1"), optional2("o2"), optional3("o3"));


## Example usage of 'experiments.inheritance':
	Bar bar = Bar.newBuilder().initR1(1).initR2(2).build();





