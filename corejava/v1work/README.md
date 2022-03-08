# About v1work
## Author: Ram Krishnan
This directory contains all the sample programs from Core Java 12th Ed book,
with modifications from the originals in corejava/v1ch02, v1ch03, v1ch04. 

## src
The Java programs are under v1work/src. There is one package demo which has
the Java code for its base class located in v1work/src/com/ramkrish/corejava/.

## out
The output classes are under v1work/out. For the package example, IntelliJ
creates a parallel directory tree under out containing the classes.

## Notes on sections 4.8.7 of the CoreJava book

### Class Path
java -classpath /home/user/classdir:.:/home/user/archives/archive.jar MyProg

Setting the CLASSPATH environment variable permanently is generally a bad
idea. People forget the global setting, and are surprised when their classes
are not loaded properly.

### Creating JAR files
jar cvf CalculatorClasses.jar *.class icon.gif

You can package application programs and code libraries into JAR files. Each
Jar file has a manifest file called MANIFEST.MF, which is located in a special
META-INF subdirectory of the JAR file. You can update the manifest using

jar ufm MyArchive.jar manifest-additions.mf

You can make the .jar file executable as follows:

jar cvfe MyProgram.jar com.mycompany.mypkg.MainAppClass files to add

You can execute the jar file as follows:

java -jar MyProgram.jar

## Multirelease JAR files
To avoid creating new jar files for every new release of a product, you can
create a multirelease JAR file. In this case, the class files that correspond
to a release are placed in a directory structure under METAINF.

For backward compatibility, version-specific class files are placed in the META-INF/versions directory, as follows:

<code>
- Application.class
- Buildingblocks.class
- META-INF
- + MANIFEST.MF with multirelease=TRUE
- +--Versions
-    + 9
-    +--- Application.class
-         Buildingblocks.class
-    + 10
-    +--- Application.class
-         Buildingblocks.class
</code>

You can add versions like this:

<code>jar uf MyProgram.jar --release 9 Application.class</code>

## JavaDoc

<code>javadoc -d docDirectory nameOfPackage</code>

<code>javadoc -link http://docs.oracle.com/javase/9/docs/api *.java</code>
will link all the classes to the API documentation.

The javadoc utility extracts information for the following items:
- Modules
- Packages
- Public classes and interfaces
- Public and protected fields
- Public and protected constructors and methods

/**
 * of free form text written like this:
 */

### Tags
- @author
- @since
- @version
- You can have <code>Code</code> sections in these comment blocks
- @see
- {@link package.class#feature label}
- {@index entry}

- @param
- @return
- @throws

## Class Design Hints
1. **Always keep data private.**

Doing anything else violates encapsulation. Data representation may change,
but how this data are used will change much less frequently. When data is
kept private, changes in their representation will not affect the users
of the class, and bugs are easier to detect.

2. **Always initialize data.**

Java won’t initialize local variables for you, but it will initialize
instance fields of objects. Don’t rely on the defaults, but initialize
all variables explicitly, either by supplying a default or by setting
defaults in all constructors.

3. **Don’t use too many basic types in a class.**.

The idea is to replace multiple related uses of basic types with other
classes. This keeps your classes easier to understand and to change.

4. **Do not overdo accessors and setters - not all fields need them.**

Not all fields may require them.

5. **Don't load a class with too many responsibilities. Split it up.**

Makes class maintenance and debugging easier.

6. **Name your classes and methods well - reflecting their responsibilities**.

Deliberate on class, private/public instance variables and methods, static
class variables and methods. 

7. **Prefer immutable classes over mutable ones**.

Immutable classes are thread-safe - they can be safely shared across multiple
threads of execution.  Mutation can happen concurrently when multiple threads
try to update an object at the same time - leading to unpredictable results.
