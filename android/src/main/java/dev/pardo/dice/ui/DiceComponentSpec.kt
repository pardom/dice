package dev.pardo.dice.ui

import android.text.Layout
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.StateValue
import com.facebook.litho.Transition
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateInitialState
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnCreateTransition
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.OnUpdateState
import com.facebook.litho.annotations.Prop
import com.facebook.litho.annotations.State
import com.facebook.litho.widget.Image
import com.facebook.litho.widget.Text
import com.facebook.litho.widget.VerticalGravity
import com.facebook.yoga.YogaJustify
import dev.pardo.dice.Drawables
import dev.pardo.dice.R
import dev.pardo.dice.app.Dice
import oolong.Dispatch

@LayoutSpec
object DiceComponentSpec {

    @OnCreateInitialState
    fun onCreateInitialState(
        context: ComponentContext,
        rollCount: StateValue<Int>
    ) {
        rollCount.set(0)
    }

    @OnUpdateState
    fun onUpdateRollCount(rollCount: StateValue<Int>) {
        rollCount.set(rollCount.get()!! + 1)
    }

    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop props: Dice.Props,
        @Prop dispatch: Dispatch<Dice.Msg>,
        @State rollCount: Int
    ): Component {
        return Column.create(context)
            .clickHandler(DiceComponent.onClickRollButton(context))
            .child(help(context, props.rolls))
            .child(roll(context, props.rolls, rollCount))
            .child(history(context, props.rolls, rollCount))
            .build()
    }

    @OnCreateTransition
    fun onCreateTransition(
        context: ComponentContext,
        @State rollCount: Int
    ): Transition {
        val historyKeys = (0..4)
            .map { i -> "dieFace${rollCount + i}" }
            .toTypedArray()

        return Transition.parallel(
            Transition.create("dieFace").animate(AnimatedProperties.ROTATION)
                .animate(AnimatedProperties.SCALE)
                .appearFrom(0F)
                .disappearTo(0F)
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0F)
                .disappearTo(0F),
            Transition.create(*historyKeys)
                .animate(AnimatedProperties.X)
                .animate(AnimatedProperties.SCALE)
                .appearFrom(0F)
                .disappearTo(0F)
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0F)
                .disappearTo(0F)
        )
    }

    private fun help(context: ComponentContext, rolls: List<Int>): Component.Builder<*>? {
        if (rolls.isNotEmpty()) return null

        return Text.create(context)
            .text("Tap or shake to roll.")
            .textSizeRes(R.dimen.abc_text_size_title_material)
            .textColorAttr(android.R.attr.textColorPrimary)
            .textAlignment(Layout.Alignment.ALIGN_CENTER)
            .verticalGravity(VerticalGravity.CENTER)
            .flexGrow(1F)
    }

    private fun roll(context: ComponentContext, rolls: List<Int>, rollCount: Int): Component.Builder<*>? {
        if (rolls.isEmpty()) return null

        return Image.create(context)
            .flexGrow(1F)
            .rotation(rollCount * 180F)
            .drawableRes(Drawables.dieFace(rolls.last()))
            .transitionKey("dieFace")
    }

    private fun history(context: ComponentContext, rolls: List<Int>, rollCount: Int): Component.Builder<*>? {
        val row = Row.create(context)
            .heightDip(96F)
            .justifyContent(YogaJustify.CENTER)
            .backgroundColor(0xFF222222.toInt())

        val history = rolls.dropLast(1)
        val historySize = history.size

        return history
            .reversed()
            .foldIndexed(row) { i, builder, roll ->
                builder.child(
                    Image.create(context)
                        .drawableRes(Drawables.dieFace(roll))
                        .widthDip(96F)
                        .heightDip(96F)
                        .transitionKey("dieFace${rollCount + historySize - i}")
                )
            }
    }

    @OnEvent(ClickEvent::class)
    fun onClickRollButton(
        context: ComponentContext,
        @Prop props: Dice.Props,
        @Prop dispatch: Dispatch<Dice.Msg>
    ) {
        dispatch(props.onUserClickedRollButton())
        DiceComponent.onUpdateRollCount(context)
    }

}
