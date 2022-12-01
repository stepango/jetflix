package com.yasinkacmaz.jetflix.util

/**
 * For testing functions we want to enforce using named params.
 *
 * To achieve that we are adding first vararg param with class that can't be
 * instantiated due to use of private constructor.
 */
class OnlyAllowNamedArgs private constructor()