// ***** This file is automatically generated from IntComparisons.java.jpp

package daikon.inv.binary.twoScalar;

import daikon.*;
import daikon.inv.*;
import daikon.inv.unary.string.*;
import daikon.inv.unary.scalar.*;
import daikon.inv.unary.sequence.*;
import daikon.inv.binary.twoScalar.*;
import daikon.inv.binary.twoSequence.*;
import daikon.derive.unary.*;
import daikon.derive.binary.*;
import daikon.suppress.*;

import utilMDE.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.*;

/**
 * Represents an invariant of "==" between two
 * long scalars.
 **/
public final class IntEqual
  extends TwoScalar implements Comparison {

  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20030822L;

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /**
   * Boolean.  True iff IntEqual invariants should be considered.
   **/
  public static boolean dkconfig_enabled = true;

  public static final Logger debug
    = Logger.getLogger("daikon.inv.binary.twoScalar.IntEqual");

  protected IntEqual(PptSlice ppt) {
   super(ppt);
  }

  protected /*@Prototype*/ IntEqual() {
   super();
  }

  private static /*@Prototype*/ IntEqual proto;

  /** Returns the prototype invariant for IntEqual **/
  public static /*@Prototype*/ IntEqual get_proto() {
    if (proto == null)
      proto = new /*@Prototype*/ IntEqual ();
    return (proto);
  }

  /** Returns whether or not this invariant is enabled **/
  public boolean enabled() {
    return dkconfig_enabled;
  }

  /** Returns whether or not the specified var types are valid for IntEqual **/
  public boolean instantiate_ok (VarInfo[] vis) {

    if (!valid_types (vis))
      return (false);

        return (true);
  }

  /** Instantiate an invariant on the specified slice **/
  protected IntEqual instantiate_dyn (PptSlice slice) /*@Prototype*/ {

    return new IntEqual (slice);
  }

  public boolean is_equality_inv() {
    return (true);
  }

  protected Invariant resurrect_done_swapped() {

      // we don't care if things swap; we have symmetry
      return this;
  }

  public boolean is_symmetric() {
    return (true);
  }

  // JHP: this should be removed in favor of checks in PptTopLevel
  // such as is_equal, is_lessequal, etc.
  // Look up a previously instantiated IntEqual relationship.
  // Should this implementation be made more efficient?
  public static /*@Nullable*/ IntEqual find(PptSlice ppt) {
    assert ppt.arity() == 2;
    for (Invariant inv : ppt.invs) {
      if (inv instanceof IntEqual)
        return (IntEqual) inv;
    }

    // If the invariant is suppressed, create it
    if ((suppressions != null) && suppressions.suppressed (ppt)) {
      IntEqual inv = proto.instantiate_dyn (ppt);
      // Fmt.pf ("%s is suppressed in ppt %s", inv.format(), ppt.name());
      return (inv);
    }

    return null;
  }

  public String repr() {
    return "IntEqual" + varNames();
  }

  public String format_using(OutputFormat format) {

    String var1name = var1().name_using(format);
    String var2name = var2().name_using(format);

    if ((format == OutputFormat.DAIKON)
        || (format == OutputFormat.ESCJAVA)
        || (format == OutputFormat.JAVASCRIPT)) {
      String comparator = "==";
      return var1name + " " + comparator + " " + var2name;
    }

    if (format.isJavaFamily()) {

        if ((var1name.indexOf("daikon.Quant.collectObject") != -1)
            ||
            (var2name.indexOf("daikon.Quant.collectObject") != -1)) {
          return "(warning: it is meaningless to compare hashcodes for values "
            + "obtained through daikon.Quant.collect... methods:"
            + var1name + " == " + var2name + ")";
        }
        return var1name + " == " + var2name;
    }

    if (format == OutputFormat.SIMPLIFY) {

        String comparator = "EQ";

      return "(" + comparator
        + " " + var1().simplifyFixup(var1name)
        + " " + var2().simplifyFixup(var2name) + ")";
    }

    return format_unimplemented(format);
  }

  public InvariantStatus check_modified(long v1, long v2, int count) {
    if (!((v1 == v2))) {
      return InvariantStatus.FALSIFIED;
    }
    return InvariantStatus.NO_CHANGE;
  }

  public InvariantStatus add_modified(long v1, long v2, int count) {
    if (logDetail() || debug.isLoggable(Level.FINE))
      log (debug, "add_modified (" + v1 + ", " + v2 + ",  "
           + "ppt.num_values = " + ppt.num_values() + ")");
    if ((logOn() || debug.isLoggable(Level.FINE)) &&
        check_modified(v1, v2, count) == InvariantStatus.FALSIFIED)
      log (debug, "destroy in add_modified (" + v1 + ", " + v2 + ",  "
           + count + ")");

    return check_modified(v1, v2, count);
  }

  // This is very tricky, because whether two variables are equal should
  // presumably be transitive, but it's not guaranteed to be so when using
  // this method and not dropping out all variables whose values are ever
  // missing.
  protected double computeConfidence() {
    // Should perhaps check number of samples and be unjustified if too few
    // samples.

      // We MUST check if we have seen samples; otherwise we get
      // undesired transitivity with missing values.
      if (ppt.num_samples() == 0) {
        return Invariant.CONFIDENCE_UNJUSTIFIED;
      }

      // It's an equality invariant.  I ought to use the actual ranges somehow.
      // Actually, I can't even use this .5 test because it can make
      // equality non-transitive.
      // return Math.pow(.5, num_values());
      return Invariant.CONFIDENCE_JUSTIFIED;

  }

  public boolean enoughSamples() {
    return (ppt.num_samples() > 0);
  }

  // For Comparison interface
  public double eq_confidence() {
    if (isExact())
      return getConfidence();
    else
      return Invariant.CONFIDENCE_NEVER;
  }

  public boolean isExact() {

      return true;
  }

  // // Temporary, for debugging
  // public void destroy() {
  //   if (debug.isLoggable(Level.FINE)) {
  //     System.out.println("IntEqual.destroy(" + ppt.name() + ")");
  //     System.out.println(repr());
  //     (new Error()).printStackTrace();
  //   }
  //   super.destroy();
  // }

  public InvariantStatus add(Object v1, Object v2, int mod_index, int count) {
    if (debug.isLoggable(Level.FINE)) {
      debug.fine("IntEqual" + ppt.varNames() + ".add("
                 + v1 + "," + v2
                 + ", mod_index=" + mod_index + ")"
                 + ", count=" + count + ")");
    }
    return super.add(v1, v2, mod_index, count);
  }

  public boolean isSameFormula(Invariant other) {
    return true;
  }

  public boolean isExclusiveFormula(Invariant other) {

    // Also ought to check against LinearBinary, etc.

      if ((other instanceof IntLessThan)
        || (other instanceof IntGreaterThan)
        || (other instanceof IntNonEqual))
      return true;

    return false;
  }

  /**
   *  Since this invariant can be a postProcessed equality, we have to
   *  handle isObvious especially to avoid circular isObvious
   *  relations.  We only check if this.ppt.var_infos imply
   *  obviousness rather than the cartesian product on the equality
   *  set.
   **/
  public /*@Nullable*/ DiscardInfo isObviousStatically_SomeInEquality() {
    if (var1().equalitySet == var2().equalitySet) {
      return isObviousStatically(this.ppt.var_infos);
    } else {
      return super.isObviousStatically_SomeInEquality();
    }
  }

  /**
   *  Since this invariant can be a postProcessed equality, we have to
   *  handle isObvious especially to avoid circular isObvious
   *  relations.  We only check if this.ppt.var_infos imply
   *  obviousness rather than the cartesian product on the equality
   *  set.
   **/
  public /*@Nullable*/ DiscardInfo isObviousDynamically_SomeInEquality() {
    if (var1().equalitySet == var2().equalitySet) {
      return isObviousDynamically(this.ppt.var_infos);
    } else {
      return super.isObviousDynamically_SomeInEquality();
    }
  }

  public /*@Nullable*/ DiscardInfo isObviousDynamically(VarInfo[] vis) {

    // JHP: We might consider adding a check over bounds.   If
    // x < c and y > c then we know that x < y.  Similarly for
    // x > c and y < c.  We could also substitute oneof for
    // one or both of the bound checks.

    DiscardInfo super_result = super.isObviousDynamically(vis);
    if (super_result != null) {
      return super_result;
    }

    VarInfo var1 = vis[0];
    VarInfo var2 = vis[1];

      // a+c=b+c is implied, because a=b must have also been reported.
      if (var1.is_add() && var2.is_add()
          && (var1.get_add_amount() == var2.get_add_amount()))
        return new DiscardInfo(this, DiscardCode.obvious,
          "Invariants of the form a+c==b+c are implied " +
          "since a==b is reported.");

    DiscardInfo di = null;

      // Check for the same invariant over enclosing arrays
      di = pairwise_implies (vis);
      if (di != null)
        return (di);

      // Check for size(A[]) == Size(B[]) where A[] == B[]
      di = array_eq_implies (vis);
      if (di != null)
        return (di);

    { // Sequence length tests
      SequenceLength sl1 = null;
      if (var1.isDerived() && (var1.derived instanceof SequenceLength))
        sl1 = (SequenceLength) var1.derived;
      SequenceLength sl2 = null;
      if (var2.isDerived() && (var2.derived instanceof SequenceLength))
        sl2 = (SequenceLength) var2.derived;

      // "size(a)-1 cmp size(b)-1" is never even instantiated;
      // use "size(a) cmp size(b)" instead.

      // This might never get invoked, as equality is printed out specially.
      VarInfo s1 = (sl1 == null) ? null : sl1.base;
      VarInfo s2 = (sl2 == null) ? null : sl2.base;
      if ((s1 != null) && (s2 != null)
          && (s1.equalitySet == s2.equalitySet)) {
        // lengths of equal arrays being compared
        String n1 = var1.name();
        String n2 = var2.name();
        return new DiscardInfo(this, DiscardCode.obvious, n1 + " and  " + n2
                            + " are equal arrays, so equal size is implied");
      }

    }

    return null;
  } // isObviousDynamically

  /**
   * If both variables are subscripts and the underlying arrays have the
   * same invariant, then this invariant is implied:
   *
   *     (x[] op y[]) ^ (i == j) ==> (x[i] op y[j])
   */
  private /*@Nullable*/ DiscardInfo pairwise_implies (VarInfo[] vis) {

    VarInfo v1 = vis[0];
    VarInfo v2 = vis[1];

    // Make sure v1 and v2 are SequenceScalarSubscript with the same shift
    if (!v1.isDerived() || !(v1.derived instanceof SequenceScalarSubscript))
      return (null);
    if (!v2.isDerived() || !(v2.derived instanceof SequenceScalarSubscript))
      return (null);
    SequenceScalarSubscript der1 = (SequenceScalarSubscript) v1.derived;
    SequenceScalarSubscript der2 = (SequenceScalarSubscript) v2.derived;
    if  (der1.index_shift != der2.index_shift)
      return (null);

    // Make sure that the indices are equal
    if (!ppt.parent.is_equal (der1.sclvar().canonicalRep(),
                              der2.sclvar().canonicalRep())) {
      return (null);
    }

    // See if the same relationship holds over the arrays
    Invariant proto = PairwiseIntEqual.get_proto();
    DiscardInfo di = ppt.parent.check_implied_canonical (this,
                                der1.seqvar(), der2.seqvar(), proto);
    return (di);
  }

  /**
   * If the equality is between two array size variables, check to see if
   * the underlying arrays are equal:
   *
   *   (x[] = y[]) ==> size(x[]) = size(y[])
   */
  private /*@Nullable*/ DiscardInfo array_eq_implies (VarInfo[] vis) {

    // Make sure v1 and v2 are size(array) with the same shift
    VarInfo v1 = vis[0];
    if (!v1.isDerived() || !(v1.derived instanceof SequenceLength))
      return (null);
    VarInfo v2 = vis[1];
    if (!v2.isDerived() || !(v2.derived instanceof SequenceLength))
      return (null);
    if (!v1.derived.isSameFormula (v2.derived))
      return (null);

    VarInfo seqvar1 = v1.derived.getBases()[0];
    VarInfo seqvar2 = v2.derived.getBases()[0];
    if (ppt.parent.is_equal (seqvar1, seqvar2))
      return new DiscardInfo (this, DiscardCode.obvious, "Implied by " +
                              seqvar1 + " == " + seqvar2 + " and "
                              + var1() + " == " + v1 + " and "
                              + var2() + " == " + v2);

    return (null);
  }

  /** NI suppressions, initialized in get_ni_suppressions() **/
  private static /*@Nullable*/ NISuppressionSet suppressions = null;

  /** Returns the non-instantiating suppressions for this invariant. **/
  public /*@Nullable*/ NISuppressionSet get_ni_suppressions() {
    return null;
  }

}
