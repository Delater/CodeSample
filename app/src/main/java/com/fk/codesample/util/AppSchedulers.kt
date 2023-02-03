package com.fk.codesample.util

import io.reactivex.Scheduler

interface AppSchedulers {
    val main: Scheduler
    val io: Scheduler
}