package dev.pardo.dice.ui

import android.text.Layout
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.Transition
import com.facebook.litho.Transition.parallel
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.animation.DimensionValue
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnCreateTransition
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.dataflow.springs.SpringConfig
import com.facebook.litho.widget.Image
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify
import dev.pardo.dice.Drawables
import dev.pardo.dice.R
import dev.pardo.dice.app.Dice
import dev.pardo.dice.app.Roll
import oolong.Dispatch

@LayoutSpec
object DiceComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(
        context: ComponentContext,
        @Prop props: Dice.Props,
        @Prop dispatch: Dispatch<Dice.Msg>
    ): Component {
        return Column.create(context)
            .backgroundRes(R.color.colorPrimary)
            .child(header(context, props.rolls))
            .child(body(context, props.rolls, props.rollCount))
            .child(footer(context, props.rolls))
            .build()
    }

    @OnCreateTransition
    fun onCreateTransition(
        context: ComponentContext,
        @Prop props: Dice.Props
    ): Transition {
        val historyKeys = props.rolls
            .map { roll -> "roll${roll.id}" }
            .toTypedArray()

        return parallel(
            Transition.create("help")
                .animate(AnimatedProperties.SCALE)
                .appearFrom(0.7F)
                .disappearTo(0.7F)
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0F)
                .disappearTo(0F),
            Transition.create("reset")
                .animate(AnimatedProperties.X)
                .appearFrom(DimensionValue.widthPercentageOffset(100F))
                .disappearTo(DimensionValue.widthPercentageOffset(100F))
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0F)
                .disappearTo(0F),
            Transition.create("roll")
                .animate(AnimatedProperties.ROTATION)
                .animate(AnimatedProperties.SCALE)
                .appearFrom(0F)
                .disappearTo(0F)
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0F)
                .disappearTo(0F),
            Transition.create("history")
                .animate(AnimatedProperties.Y)
                .animator(Transition.SpringTransitionAnimator(SpringConfig.noOvershootConfig))
                .appearFrom(DimensionValue.widthPercentageOffset(100F))
                .disappearTo(DimensionValue.widthPercentageOffset(100F)),
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

    @OnEvent(ClickEvent::class)
    fun onClickRollButton(
        context: ComponentContext,
        @Prop props: Dice.Props,
        @Prop dispatch: Dispatch<Dice.Msg>
    ) {
        dispatch(props.onUserClickedRollButton())
    }

    @OnEvent(ClickEvent::class)
    fun onClickResetButton(
        context: ComponentContext,
        @Prop props: Dice.Props,
        @Prop dispatch: Dispatch<Dice.Msg>
    ) {
        dispatch(props.onUserClickedResetButton())
    }

    private fun header(context: ComponentContext, rolls: List<Roll>): Component.Builder<*>? {
        val header = Row.create(context)
            .reverse(true)
            .heightDip(80F)
            .paddingDip(YogaEdge.ALL, 16F)

        if (rolls.isEmpty()) return header

        return header
            .child(headerReset(context))
    }

    private fun headerReset(context: ComponentContext): Component.Builder<*>? {
        return Image.create(context)
            .widthDip(48F)
            .heightDip(48F)
            .clickHandler(DiceComponent.onClickResetButton(context))
            .transitionKey("reset")
            .drawableRes(R.drawable.ic_reset_24dp)
    }

    private fun body(context: ComponentContext, rolls: List<Roll>, rollCount: Int): Component.Builder<*>? {
        val rollFace = rolls.lastOrNull()?.face ?: 3
        return Image.create(context)
            .flexGrow(1F)
            .rotation(rollCount * 180F)
            .clickHandler(DiceComponent.onClickRollButton(context))
            .transitionKey("roll")
            .drawableRes(Drawables.dieFace(rollFace))
    }

    private fun footer(context: ComponentContext, rolls: List<Roll>): Component.Builder<*>? {
        val footer = Row.create(context)
            .heightDip(96F)
            .justifyContent(YogaJustify.CENTER)

        if (rolls.isEmpty()) {
            return footer
                .child(footerHelp(context))
        }

        val history = footer
            .backgroundRes(R.color.colorPrimaryDark)
            .transitionKey("history")

        return rolls
            .dropLast(1)
            .reversed()
            .take(3)
            .fold(history) { builder, roll ->
                builder.child(footerHistoryRoll(context, roll))
            }
    }

    private fun footerHelp(context: ComponentContext): Component.Builder<*>? {
        return Text.create(context)
            .flexGrow(1F)
            .text("Tap to roll")
            .textAlignment(Layout.Alignment.ALIGN_CENTER)
            .textColorAttr(android.R.attr.textColorPrimary)
            .textSizeRes(R.dimen.abc_text_size_display_1_material)
            .transitionKey("help")
    }

    private fun footerHistoryRoll(context: ComponentContext, roll: Roll): Component.Builder<*>? {
        return Image.create(context)
            .widthDip(96F)
            .heightDip(96F)
            .transitionKey("roll${roll.id}")
            .drawableRes(Drawables.dieFace(roll.face))
    }
}
