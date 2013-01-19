/*
    FP Quark
    Copyright 2012 - 2013 Miguel Negrão.

    FP Quark: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FP Quark is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with FP Quark.  If not, see <http://www.gnu.org/licenses/>.

    It is possible to add more type instances by adding the functions
    directly to the dict from the initClass function of the class that
    one wants to make an instance of some type class.
*/

/*

DoNotationUnitTest().run

*/


DoNotationUnitTest : UnitTest {

    test_processDoBlock {

        //stuff that should work
        this.assertEquals( DoNotation.processDoBlock("a; b"), "a >>= {  b }".success );
        this.assertEquals( DoNotation.processDoBlock("x <- a; b"), "a >>= { |x|  b }".success );
        this.assertEquals( DoNotation.processDoBlock("x <- a; return b"), "a.collect{ |x| b }".success);
        this.assertEquals( DoNotation.processDoBlock("x <- a; let v = x + 1; b"), "a >>= { |x| var v = x + 1; b }".success );
        this.assertEquals( DoNotation.processDoBlock("x <- a; let v = x + 1; return b"), "a.collect{ |x| var v = x + 1;b }".success );
        this.assertEquals( DoNotation.processDoBlock("x <- a ||| g1; return x "), "a.select { |x|  g1 }.collect{ |x| x }".success );
        this.assertEquals( DoNotation.processDoBlock("x <- a; let b = c; y <- d; let e = f; g"),"a >>= { |x| var b = c;d >>= { |y| var e = f; g } } ".success);
        //returns should be checked after last semicolon
        this.assertEquals( DoNotation.processDoBlock("a.return; b"),"a.return >>= {  b }".success);


        //stuff that should fail
        this.assertEquals( DoNotation.processDoBlock("a").class, Failure, "Do(a) should fail");
        this.assertEquals( DoNotation.processDoBlock("a;").class, Failure, "Do(a;) should fail");
        this.assertEquals( DoNotation.processDoBlock("a; <- <-").class, Failure, "Do(a; <- <-) should fail");
        this.assertEquals( DoNotation.processDoBlock("a <- expr1; b <- expr2").class, Failure, "Do(a <- expr1; b <- expr2) should fail");
    }

    test_removeComments {

        this.assertEquals( DoNotation.removeComments("hello/*to a lovelly */ world"), "hello world");
        this.assertEquals( DoNotation.removeComments("hello // world"), "hello ");
    }

    test_processString {

        this.assertEquals( DoNotation.processString("Do(a;b); Do(c;d)"), "a >>= { b }; c >>= { d }".success);
        this.assertEquals( DoNotation.processString("Do(a; let v = Do(c;d); return v +a)"), "a.collect{ var v = c >>= { d };v +a }".success);

        this.assertEquals( DoNotation.processString("Do(").class, Failure, "Do( should fail");
    }


}

