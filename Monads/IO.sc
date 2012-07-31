/*
    FP Quark
    Copyright 2012 Miguel Negrão.

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
*/

IO{
	var <func;
	*new{ |func| ^super.newCopyArgs(func) }

	*initClass{
		Class.initClassTree(TypeClasses);
		//type instances declarations:
		TypeClasses.addInstance(this,
			(
				'fmap': { |fa,f| IO{ f.(fa.value) } },
				'bind': { |fa,f| IO{ f.(fa.value).value } },
				'pure': { |a| IO{ a } }
			)
		);
	}
	
	*activate {
		//like ghci, calls unsafePerformIO on any IO returned
		thisProcess.interpreter.codeDump = { |str, val, func|
			//[str, val, func].postcs;
			val.tryPerform(\unsafePerformIO)
		};
	}	
	
	*deactivate {
		thisProcess.interpreter.codeDump = nil;
	}	


	unsafePerformIO{ ^func.value }
	value{ ^func.value }
}

+ Object {
	
	putStrLn { ^IO{ postln(this) } }

}

+ QWindow {
	frontIO {
		^IO{ this.front }
	}
	
	closeIO {
		^IO{ this.close }
	}
	
	setPropIO { |...args| //selector, args
		^IO{ this.performMsg(args) }
	}
}

+ QView {
	frontIO {
		^IO{ this.front }
	}
	
	closeIO {
		^IO{ this.close }
	}
	
	setPropIO { |...args| //selector, args
		^IO{ this.performList(*args) }
	}
}

