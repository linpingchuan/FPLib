
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