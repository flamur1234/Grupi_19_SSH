<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class AdminiKryesorController extends Controller
{
    public function getAdminKryesor(Request $request, $first_name, $pass){
        $admin = DB::table('adminikryesor')->where([['first_name', $first_name], ['pass', $pass]])->get();
        return response()->json($admin); 
    }
}
