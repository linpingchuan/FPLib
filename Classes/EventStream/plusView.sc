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

+ QView {

	mouseDownES {
		var es = EventSource();
		this.mouseDownAction_({ |view, x, y, modifiers, buttonNumber, clickCount|
			es.fire( view, x, y, modifiers, buttonNumber, clickCount );
		});
		^es
	}

	mouseUpES {
		var es = EventSource();
		this.mouseUpAction_({ |view, x, y, modifiers|
			es.fire( view, x, y, modifiers);
		});
		^es
	}

	mouseEnterES { }
	mouseLeaveES { }

    mouseMoveES {
   		var es = EventSource();
		this.mouseMoveAction_({ |view, x, y, modifiers|
			es.fire( Tuple4(view, x, y, modifiers) );
		});
		^es
	}
	
	 mouseMoveEN {
   		^this.mouseMoveES.asENInput
	}

    mouseOverES { }
    mouseWheelES { }
    keyDownES { }
    keyUpES { }
    keyModifiersChangedES { }

}