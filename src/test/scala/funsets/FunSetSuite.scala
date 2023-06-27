package funsets

import org.junit.Assert.{assertFalse, assertTrue}

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s5 = singletonSet(5)
    val s6 = singletonSet(1)

    val sets1 = (x: Int) => x >= -100 && x <= 100

  /**
   * This test is currently disabled (by using .ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */

  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(contains(s2, 2), "Singleton")
      assert(contains(s3, 3), "Singleton")
      assert(contains(s5, 5), "Singleton")

      assert(!contains(s5, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val uS = union(s1, s2)
      assert(contains(uS, 1), "Union 1")
      assert(contains(uS, 2), "Union 2")
      assert(!contains(uS, 3), "Union 3")

      val uS2 = union(s3, s5)
      assert(contains(uS2, 3), "Union 3")
      assert(contains(uS2, 5), "Union 5")
      assert(!contains(uS2, 1), "Union 1")
  }

  test("intersection contains all elements of each set") {
    new TestSets {
      val iS = intersect(s1, s6)
      assert(iS(1))
      assert(!iS(2))
    }
  }

  test("difference of all elements of `s` that are not in `t`") {
    new TestSets {
      val dS = diff(s1, s2)
      assert(dS(1))
      assert(!dS(2))

      val dS2 = diff(s1, s6)
      assert(!dS2(1))
    }
  }

  test("filter of all elements of `s` for which `p` holds") {
    new TestSets {
      val p = (x: Int) => x % 2 == 0

      val fS = filter(s1, p)
      assert(!fS(1))

      val fS2 = filter(s2, p)
      assert(fS2(2))
    }
  }

  test("forall returns whether all bounded integers within `s` satisfy `p`") {
    new TestSets {

      val p1 = (x: Int) => x >= -100 && x <= 100
      val p2 = (x: Int) => x >= 5

      val forAllS1 = forall(sets1, p1)
      assertTrue(forAllS1)

      val forAllS2 = forall(sets1, p2)
      assertFalse(forAllS2)
    }
  }

  test("exists a bounded integer within 's' that satisfies `p`") {
    new TestSets {
      val p1 = (x: Int) => x == 1
      val p2 = (x: Int) => x >= 5

      val eS1 = exists(sets1, p1)
      assertEquals(eS1, true)

      val eS2 = exists(sets1, p2)
      assertEquals(eS2, true)

    }
  }

  test("exists a bounded integer within 's' that satisfies `p`") {
    new TestSets {
      val f = (x: Int) => x * 2
      
      
    }
    
  }


  import scala.concurrent.duration.*

  override val munitTimeout = 10.seconds
