package org.kata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ASimpleTest {

    @Test
    fun `a test`() {
        assertThat(ASimpleClass().yes()).isTrue()
    }
}
