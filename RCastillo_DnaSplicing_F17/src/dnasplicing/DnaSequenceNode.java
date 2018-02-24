//============================================================================
// Name        : RCastillo_DnaSplicing_F17
// Author      : [CastleBombs + Strenn]
// Date        : (FRI, 27 OCT 17)
// Class	   	   : cs 106, JavA Theory && Practice II 
// Instructor  : Professor Stephen Strenn
//============================================================================

package dnasplicing;

public class DnaSequenceNode {
	public String dnaSequence;
	public DnaSequenceNode previous;
	public DnaSequenceNode next;

	public DnaSequenceNode(String initialDnaSequence) {
		dnaSequence = initialDnaSequence;
	}
}
