import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StackUtilsTest {
    @Test
    fun `Should be able to convert stacks to and from strings`() {
        val stack = listOf(Cup(1, Orientation.DOWN), Cup(2, Orientation.UP))

        stack.toStackString() shouldBe "n1 u2"
        stack.toStackString().toStack() shouldBe stack
    }

    @Test
    fun `Should correctly invert a stack`() {
        val startingStack = "n4 n3 u2 n1"
        val inverted = startingStack.toStack().invert()
        inverted.toStackString() shouldBe "u1 n2 u3 u4"
    }

    @Test
    fun `Should generate the correct number of stacks for a given number of cups`() {
        generateStacks(2).size shouldBe 8
        generateStacks(3).size shouldBe 48
        generateStacks(4).size shouldBe 384
    }

    @Test
    fun `Generated stacks should be unique`() {
        generateStacks(3).toSet().size shouldBe generateStacks(3).size
    }

    @Test
    fun `Should generate the expected stacks for n = 2`() {
        val expectedStacks = listOf(
            "u1 u2",
            "u1 n2",
            "n1 u2",
            "n1 n2",
            "u2 u1",
            "u2 n1",
            "n2 u1",
            "n2 n1"
        )

        val stacks = generateStacks(2).map { it.toStackString() }
        stacks.shouldContainExactlyInAnyOrder(expectedStacks)
    }

    @Test
    fun `Should correctly identify nested vs unnested stacks`() {
        val allStacks = generateStacks(2)

        val nestedStacks = allStacks.filter { it.hasNesting() }.map { it.toStackString() }
        nestedStacks.shouldContainExactlyInAnyOrder(
            "u2 u1",
            "n1 n2"
        )
    }
}
