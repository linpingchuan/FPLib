// Inspired on http://hackage.haskell.org/packages/archive/mtl/1.1.0.2/doc/html/src/Control-Monad-Reader.html

WriterReader{
	var <func; // r -> (a,w)
	
	*initClass{
		Class.initClassTree(TypeClasses);
		//type instances declarations:
		TypeClasses.addInstance(this,
			(
				'fmap': { |fa,f| fa.collect(f) },
				'bind': { |fa,f| fa.flatCollect(f) },
				'pure': { |a,class| WriterReader( { Tuple2(a, class !? _.zero ?? {[]})  } ) }
			)
		);
	}
	
	*new { |f| ^super.newCopyArgs(f) }
	
	*ask { |class| ^WriterReader( Tuple2(_,class.zero) ) }
	
	run { |...args|
		^func.value(*args)	
	}
	
	//change the environment locally
	local { |f| ^Reader( func <> f ) }
	
	collect { |f|
		^WriterReader( { |r|
			var aw = func.(r);
			Tuple2(f.(aw.at1), aw.at2)
		} )	
	}
	
	flatCollect { |f|
		^WriterReader( { |r| 
			var aw1 = func.(r);
			var aw2 = f.(aw1.at1).run(r);
			Tuple2(aw2.at1, aw1.at2 |+| aw2.at2)
		} )
	}
	
	tell { |x|	
		^Reader( { |r|
			var aw = func.(r);
			Tuple2(aw.at1, aw.at2 |+| x)
		} )	
	}

}

+ Writer {

	asWriterReader {
		^WriterReader( { |x| Tuple2(a,w) } )
	}
}
	

/*

5.pure(WriterReader).tell(["hello"]).run(3)

WriterReader.ask(Array).run(3)
WriterReader.ask(Array).tell(["abc9"]).run(3)
WriterReader.ask(Array).tell(["abc9"]).local(_+1).run(3)


*/
