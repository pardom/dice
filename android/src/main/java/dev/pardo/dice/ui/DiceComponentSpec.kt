package dev.pardo.dice.ui

import android.text.Layout
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.Transition
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnCreateTransition
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Image
import com.facebook.litho.widget.Text
import com.facebook.litho.widget.VerticalGravity
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
            .clickHandler(DiceComponent.onClickRollButton(context))
            .child(help(context, props.rolls))
            .child(roll(context, props.rolls, props.rollCount))
            .child(history(context, props.rolls))
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

        return Transition.parallel(
            Transition.create("dieFace").animate(AnimatedProperties.ROTATION)
                .animate(AnimatedProperties.SCALE)
                .appearFrom(0F)
                .animate(AnimatedProperties.ALPHA)
                .appearFrom(0F),
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

    private fun help(context: ComponentContext, rolls: List<Roll>): Component.Builder<*>? {
        if (rolls.isNotEmpty()) return null

        return Text.create(context)
            .text("Tap to roll")
            .textSizeRes(R.dimen.abc_text_size_display_1_material)
            .textColorAttr(android.R.attr.textColorPrimary)
            .textAlignment(Layout.Alignment.ALIGN_CENTER)
            .verticalGravity(VerticalGravity.CENTER)
            .flexGrow(1F)
    }

    private fun roll(context: ComponentContext, rolls: List<Roll>, rollCount: Int): Component.Builder<*>? {
        if (rolls.isEmpty()) return null

        return Image.create(context)
            .flexGrow(1F)
            .rotation(rollCount * 180F)
            .drawableRes(Drawables.dieFace(rolls.last().face))
            .transitionKey("dieFace")
    }

    private fun history(context: ComponentContext, rolls: List<Roll>): Component.Builder<*>? {
        val row = Row.create(context)
            .heightDip(96F)
            .justifyContent(YogaJustify.CENTER)
            .backgroundColor(0xFF222222.toInt())

        return rolls
            .dropLast(1)
            .reversed()
            .take(3)
            .fold(row) { builder, roll ->
                builder.child(
                    Image.create(context)
                        .drawableRes(Drawables.dieFace(roll.face))
                        .widthDip(96F)
                        .heightDip(96F)
                        .transitionKey("roll${roll.id}")
                )
            }
    }

}
