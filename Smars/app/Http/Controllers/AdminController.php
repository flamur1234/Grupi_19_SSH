<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class AdminController extends Controller
{
    public function getAdmin(Request $request, $firstName, $password){
        $admins = DB::table('admin')->where([['firstName', $firstName], ['password', $password]])->get();
        return response()->json($admins);
    }

    public function getAllAdmins(Request $request){
        $allAdmins = DB::table('admin')->get();
        return response()->json($allAdmins);
    }

    public function addAdmin(Request $request) {
        DB::table('admin')->insert([
            'adminId' => $request->adminId,
            'firstName' => $request->firstName,
            'lastName' => $request->lastName,
            'email' => $request->email,
            'password' => $request->password
        ]);
    }

    public function updateAdmin(Request $request, $adminId, $firstName, $lastName,$email, $password) {
        $update =DB::table('admin')
         ->where('adminId', $adminId)
         ->update([
                'firstName' => $firstName,
                'lastName' => $lastName,
                'email' => $email,
                'password' => $password
         ]);
         if(strlen($update) > 0) 
          return response()->json(['status' => 'success'], 200);
         return response()->json(['status' => 'error'], 500);
     }
     public function deleteAdmin(Request $request, $adminId) {
       $delete =  DB::table('admin')
         ->where('adminId', $adminId)
         ->delete();
         if(strlen($delete) > 0) 
          return response()->json(['status' => 'success'], 200);
         return response()->json(['status' => 'error'], 500);
     }
}
