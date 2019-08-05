package dev.pardo.dice

import oolong.Dispatch
import oolong.Render

class RenderProxy<Msg, Props> {

    private var props: Props? = null
    private var dispatch: Dispatch<Msg>? = null
    private var delegate: Render<Msg, Props>? = null

    val render: Render<Msg, Props> = { props, dispatch ->
        this.props = props
        this.dispatch = dispatch
        delegate?.invoke(props, dispatch)
    }

    fun setDelegate(delegate: Render<Msg, Props>) {
        this.delegate = delegate
        if (props != null && dispatch != null) {
            delegate.invoke(props!!, dispatch!!)
        }
    }

    fun clearDelegate() {
        delegate = null
    }

}
