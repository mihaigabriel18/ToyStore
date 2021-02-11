# **Toy Store**

#### **_Name and Surname: Mihai-Gabriel Calitescu_**
#### **_Group and Series: 321CB_**

Project simulates the behaviour af a functional toy store, named _Mihai's Store_. Our store has a numerous list
of products in its deposit. Each product has manufacturer associated to it, a designer which created that product,
one manufacturer can have multiple product associated with it. Store also holds a global currency for which all
of our products prices will be calculated (if store's currency is GBP products prices are only in £). There is also the
possibility of applying a discount to products in our store, each discount being either one with fixed value (-15 RON)
or one with percentage reduction (-40%). Both currencies and discounts need to be firstly added to a collection
that keeps track of valid discounts/currencies before actually using them, trying to use one of them without
adding them first will result in an exception. Because there can only be one store, one instance of the _Store class_
we have implemented it as a **Singleton**, using the singleton design pattern.

When the store opens, the products and all fields will be initialized from a datasheet, _a csv file_, which
we will have to parse according to our store needs. Some fields of the csv will be useless for us, that is why
package _toystore.parser_ contains enumeration _FieldsOfInterest_, which lists the header columns that present an
interest to us for our store. The helper class _ParserCSV_ from the same package has 2 public methods that
are able to read/write the csv file from/to a List of lists of strings (a way of organising a matrix-like data
structure using only collections). This package also has 2 custom unchecked exceptions used to stipulate csv
writing/reading exception (unchecked because we do not want to declare them explicitly throughout the whole
calling process, diminishing the exhaustive process, also, they are crucial to the functionality of our
store, so throwing one of those exceptions cannot be easily solved and dealt with inside a try/catch, if
they appear they will be thrown and there is no recovery from there). In the same manner our store has the possibility
of writing a store state into a csv file, from which we can read back later (reading 2 times from csv files will result
in wiping the past records of the store, basically overwriting the store state with the new one, I have chosen this
implementation instead of overwriting each product and throwing a _DuplicateProductException_ exception on every 
product that was already in the store database (which is for every product). This means that is have chosen
to renew the store instead of throwing around ~8000 exceptions every time another csv is read, this would have made
sense if one of the commands would delete/add products to the store, but since there are no such commands, it seems
a forced logic that doesn't fit with the intended behaviour of the store). The commands _LoadCSV_ and _SaveCSV_
highlight these 2 functionalities. While writing to csv file fields that do not have a manufacturer associated
with them will be assigned a default manufacturer named **Not Available**

One more option for our store is to save its state into a binary file, to serialize it state and the option to
de-serialize it later one. That is why _Store_ and every class in store implement _Serializable_. These options come
with the commands _SaveStore_ and _LoadStore_, which receive a filename to write the binary file to or from. These are
the **BONUS** commands.

Because the Product class is such a verbose class, with a lot of fields that need to be initialized by the constructor,
I have implemented a _ProductBuilder_ which uses the builder design pattern to initialize only the fields we want from
the class. Each initializer method starts with _with_ and returns the ProductBuilder object, this so that methods 
can be chained together, and must end with _build()_ methods to return the actual _Product_ instance.

I have implemented Comparable inside the Manufacturer class because the end user will require the manufacturers
to be printed in ascending order (with the command _listmanufacturers_). Class _Store_ will have a collection
of manufacturers, from all products in the csv, and one method will sort these manufacturers as strings and print them.
I have chosen a HashMap as implementation for the collection since there is going to be the need of retrieving 
manufacturers by their names (Strings), and instead of storing them inside a List and querying in O(n) time, a hashmap
allows us to query in O(1).

For Currency and Discount the end-user has 2 options: add a currency/ discount or applying a currency/discount.
Using one of the those without adding it first will result in a _CurrencyNotFound_/_DiscountNotFound_ _Exception_,
so each one of these classes will keep a static map from key to Manufacturer/Currency object. The currency map
will take the name of the currency as a key and for Discount the key is a pair of type and value, since those
determine the uniqueness of a discount. I have chosen to put these maps as static in the classes instead to put them
inside _Store_ or _Main_ because, since store is a singleton there will never be two different stores using the same
Currency or Discount class.

In the _Store_ class there are some methods that maybe harder to understand to the naked eye. The _applyDiscount()_
method will firstly check if the discount has been added to the map before applying it, and one more check:
retrieving the minimum price from all the objects, in case of FIXED_DISCOUNT if the value of the discount is 
bigger than the minimum, _NegativePriceException_ will be thrown and no discount will be applied, the same can
be said for a PERCENTAGE_DISCOUNT which is not between 0-100. The other method I have made modification to is
_calculateTotal()_, for which I check if we request more products than the actual stock capacity of the store
(if _eac7efa5dbd3d667f26eb3d3ab504464_ is requested 7 times the exception _RequestTooManyProducts_ will be thrown
because the store only holds 5 copies of that product).

If the currency used in the csv file (GBP in our case) has not been yet created by using _createCurrency GBP ...._ 
then "CurrencyNotFoundException" is going to be thrown, if it has been created, but the store's currency
is not in gbp, then the prices from the csv are going to be automatically converted to the store's currency,
which at the very start is EURO, with a parity of 1 relative to itself.

In the _toystore.financial_ package I have created the exception "CannotChangeParityException" which is thrown
when we try to change Euro's parity to itself, which doesn't make sense, and it is an unwanted behaviour.

Getting to the command part of the project, I have implemented a Command design pattern (for the **BONUS**), an
abstract command, an interface (_Command_) that has only 1 method _execute()_ which is going to be overridden by each
specific command class to perform a specific action. The _Console_ class is a **Utility class**, a non-instantiable
class with a static method to add commands to a queue of execution, and a method that is called after parsing every
input command, which will execute every command in the queue and empty it. I have organised the commands in different
packages that indicate the usage of that command. The specific command class will initialize its parameters in the
constructor and use them int the _execute()_ method.

The entry-point of the program is the _Main_ method from the _testing_ package, which will parse the input and
add command to the queue of execution. After parsing every command the queue is going to be executed all at once, so
the output is going to be displayed only when all commands have been introduced.

The input is going to be read from the keyboard and displayed in a file called "commandResults.out", and the errors that
occur while running those commands are redirected to "commandErrors.err".

In the folder tests/res/ there are the 2 csv files (sample and cleaned), and the 5 tests I have tried my program on.
In the folder tests/res/ there are 5 folders one for each test, and each one is going to contain the output
resulted after running this project on that specific test. I have moved the outputs manually there so that it is easy
to correct them.

I have also included Makefile in my project and the maven specific "pom.xml", which has the plugins for generating the
jar with all the dependencies of the project. How to run the project: 

make run1 \ make run2 \ make run3 \ ....

Again the output of these command will be in the 2 files in the base of the program.