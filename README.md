# simple-query

I've been writing several simple apps in Clojure and I always find
myself writing the same macros to quickly grab data from Sql
Server. And while that's probably not the most common data store for
jvm projects, its where this project starts. Over time more
connections will be added but at first it will be just Sql Server.

The idea is that I have straightforward sql and I just want to grab my
data. This is not a full-fledged orm, has almost zero features. In
fact, these are just a very thin veneer over the jdbc interface
itself. But they have proven their worth so far for me. Its almost not
worth creating except that I find myself rewriting these same macros
in every project so far.

There are many different clojure libraries, and they are all
great. However, I find myself using more complicated queries that
other team mates write and its often difficult or impossible to
translate these queries into whichever flavor of abstraction a library
provides. I've found that SQL is the lingua franca that everyone can
work with, especially if they've never used Clojure.

## Usage

Use the function `sql-server-conn` with a connection string to create
a map holding all the database information with `(sql-server-conn
users-db)`. The argument should be a jdbc style connection string of
the form

//servername;database=dbname;user=username;password=password

All of the convenience comes from the `make-query` macro. It takes the
name of the function you want to create, the connection you want to
use, the query text and the parameters for the text. In the sql query
use `?` for replacement just as ususal with jdbc. The order of the
parameters must match the order they will be used in the query.

Eg,

    (make-query get-all-users users-db "select * from users")

or

    (make-query get-user-by-id users-db
                               "select * from users where id =?"
                               id)

    (make-query another-user-query  users-db
                                    "select * from users
                                     where status = ?
                                     and startDate > ? "
                                    status
                                    date)

The macro isn't incredibly complicated but is nice when I'm
doing quick projects with simple gets. If you use eldoc you'll have
the information in the minibuffer so you can keep parameters
straightforward.

These always return sequences of db rows. You can make queries that
return individual records with the macro `make-singular-query`, which
just simply does what the other one does and returns the first element.


## Extending

Pull requests are happily accepted. The only thing that binds this to
SqlServer is the specific function that returns the jdbc map
specifying sql server. Create a new one for the new data store and you
are golden. I don't have access to any at the moment but I'd imagine
that they pop up quickly.

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
