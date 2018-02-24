//============================================================================
// Name        : RCastillo_DnaSplicing_F17
// Author      : [CastleBombs + Strenn]
// Date        : (FRI, 27 OCT 17)
// Class	   	   : cs 106, JavA Theory && Practice II 
// Instructor  : Professor Stephen Strenn
//============================================================================

package dnasplicing;

/**
 * The LinkedDnaStrand class implements an application that creates LDS objects
 * through 0 parameters || a single string parameter with nested information.
 */
public class LinkedDnaStrand implements DnaStrand {

	int nodeCount;
	int appendCount;
	DnaSequenceNode top;
	DnaSequenceNode cursor;
	StringBuilder search;

	public LinkedDnaStrand() {
	}

	/**
	 * NOTE: Your LinkedDnaStrand class must have a constructor that takes one
	 * parameter: String dnaSequence. When the constructor completes, your linked
	 * list should have just one node, and it should contain the passed-in
	 * dnaSequence. For example, if the following line of code was executed:
	 * 
	 * LinkedDnaStrand strand = new LinkedDnaStrand("GATTACA");
	 * 
	 * Then strand's linked list should look something like (previous pointers not
	 * shown):
	 * 
	 * first -> "GATTACA" -> null
	 * 
	 * The first line of this constructor should look like:
	 * 
	 * public LinkedDnaStrand(String dnaSequence) {
	 */
	// Constructor created by LDnaSTester
	public LinkedDnaStrand(String dnaSequence) {

		DnaSequenceNode newNode = new DnaSequenceNode(dnaSequence);
		top = newNode;
		cursor = newNode;

		nodeCount++;
	}

	/**
	 * Returns the number of nucleotides in this strand.
	 * 
	 * @return the number of base-pairs in this strand
	 */
	@Override
	public long getNucleotideCount() {

		DnaSequenceNode gNC_cursor = top;
		StringBuilder sb_nucleotideCount = new StringBuilder();

		while (gNC_cursor != null) {
			sb_nucleotideCount.append(gNC_cursor.dnaSequence); // need to use the str in DSN
			gNC_cursor = gNC_cursor.next;
		}
		return sb_nucleotideCount.length();
	}

	/**
	 * Appends the given dnaSequence on to the end of this DnaStrand. appendCount is
	 * incremented. Note: If this DnaStrand is empty, append() should just do the
	 * same thing as the constructor. In this special case, appendCount is not
	 * incremented.
	 * 
	 * @param dnaSequence
	 *            is the DNA string to append
	 */
	@Override
	public void append(String dnaSequence) {

		// str into -> DnaSequnceNode
		DnaSequenceNode dnaToAppend = new DnaSequenceNode(dnaSequence);

		// Add to likedList @ curor, then move cursor
		cursor.next = dnaToAppend;
		cursor.next.previous = cursor;
		if (cursor.dnaSequence.length() != 0) {
			appendCount++;
		}
		cursor = dnaToAppend;
		cursor.next = null;

		nodeCount++;

	}

	/**
	 * This method creates a <bold>new</bold> DnaStrand that is a clone of the
	 * current DnaStrand, but with every instance of enzyme replaced by splicee. For
	 * example, if the LinkedDnaStrand is instantiated with "TTGATCC", and
	 * cutSplice("GAT", "TTAAGG") is called, then the linked list should become
	 * something like (previous pointers not shown):
	 * 
	 * first -> "TT" -> "TTAAGG" -> "CC" -> null
	 * 
	 * <b>NOTE</b>: This method will only be called when the linked list has just
	 * one node, and it will only be called once for a DnaStrand. This means that
	 * you do not need to worry about searching for enzyme matches across node
	 * boundaries.
	 * 
	 * @param enzyme
	 *            is the DNA sequence to search for in this DnaStrand.
	 * 
	 * @param splicee
	 *            is the DNA sequence to append in place of the enzyme in the
	 *            returned DnaStrand
	 * 
	 * @return A <bold>new</bold> strand leaving the original strand unchanged.
	 */
	@Override
	public DnaStrand cutSplice(String enzyme, String splicee) {

		LinkedDnaStrand newLStrand = null; // +++ <----
		search = new StringBuilder(toString());

		int pos = 0;
		int start = 0;
		boolean bool = true;

		while ((pos = search.indexOf(enzyme, pos)) >= 0) {
			if (bool) { // [0]--[1st.match]
				if (pos == 0)
					newLStrand = new LinkedDnaStrand(splicee);
				else
					newLStrand = new LinkedDnaStrand(search.substring(start, pos));

				bool = false;
			} else {
				// found 2nd enzyme
				newLStrand.append(search.substring(start, pos));
			}

			start = pos + enzyme.length();
			if (pos != 0)
				newLStrand.append(splicee);
			pos++;
		} // w

		if (start < search.length()) {
			// if enzyme never found, return empty str
			if (newLStrand == null) {
				newLStrand = new LinkedDnaStrand("");
			} else { // adds the rest_ends
				newLStrand.append(search.substring(start));
			}
		}

		return newLStrand;
	} // cS

	/**
	 * Returns a <bold>new</bold> DnaStrand that is the reverse of this strand,
	 * e.g., if this DnaStrand contains "CGAT", then the returned DnaStrand should
	 * contain "TAGC".
	 * 
	 * @return A <bold>new</bold> strand containing a reversed DNA sequence.
	 */
	@Override
	public DnaStrand createReversedDnaStrand() {

		String str = toString();
		StringBuilder _sb = new StringBuilder();
		_sb = _sb.append(str).reverse();

		LinkedDnaStrand reverseStrand = new LinkedDnaStrand(_sb.toString());

		return reverseStrand;
	}// cRDS

	public static String reverse(String in) {
		char[] rev = in.toCharArray();
		int start = 0, last = rev.length - 1;
		char _temp;
		while (last > start) {
			_temp = rev[start];
			rev[start] = rev[last];
			rev[last] = _temp;
			last--;
			start++;
		}
		return new String(rev);
	}

	/**
	 * 
	 * @return The number of times that the DnaStrand has been appended via a call
	 *         to append() or during the cutSplice() operation. Note that the very
	 *         first time that a DnaStrand is given a DNA sequence is not to be
	 *         counted as an append.
	 */
	@Override
	public int getAppendCount() {
		return appendCount;
	}

	/**
	 * This is a utility method that allows the outside world direct access to the
	 * nodes in the linked list.
	 * 
	 * @return The first DnaSequenceNode in the linked list of nodes.
	 */
	@Override
	public DnaSequenceNode getFirstNode() {
		return top;
	}

	/**
	 * This is a utility method that allows the outside world to determine the
	 * number of nodes in the linked list.
	 * 
	 * @return
	 */
	@Override
	public int getNodeCount() {
		return this.nodeCount;
	}

	/**
	 * adding this from STEP #3
	 * 
	 * @return The entire DNA sequence represented by this DnaStrand.
	 */
	@Override
	public String toString() {
		if (top == null)
			return null;

		DnaSequenceNode cur_ = top;
		String sb_ = "";
		String add = "";

		while (cur_ != null) {
			add = cur_.dnaSequence;
			sb_ = sb_ + add;
			cur_ = cur_.next;
		}

		System.out.printf(sb_);
		return sb_;
	}
}// LDS
