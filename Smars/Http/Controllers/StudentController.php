<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class StudentController extends Controller
{
    public function getStudents(Request $request, $kf) {
        $students = DB::table('student')->where('kf', $kf)->get();
        return response()->json($students);
    }

    public function test() {
        return response()->json('test');
    }

    public function addStudent(Request $request) {
        DB::table('student')->insert([
            'nid' => ($request->nid),
            'eid' => $request->eid,
            'mid' => $request->mid,
            'aid' => $request->aid,
            'gid' => $request->gid,
            'fid' => $request->fid,
            'did' => $request->did,
            'eeid' => $request->eeid,
            'tid' => $request->tid,
            'kf' => 0,
        ]);
    }
    public function registerStudent(Request $request, $nid, $kf) {
       $update =DB::table('student')
        ->where('nid', $nid)
        ->update([
            'kf' => $kf
        ]);
        if(strlen($update) > 0) 
         return response()->json(['status' => 'success'], 200);
        return response()->json(['status' => 'error'], 500);
    }
    public function deleteStudent(Request $request, $nid) {
      $delete =  DB::table('student')
        ->where('nid', $nid)
        ->delete();
        if(strlen($delete) > 0) 
         return response()->json(['status' => 'success'], 200);
        return response()->json(['status' => 'error'], 500);
    }

    public function updateStudent(Request $request, $nid, $eid, $mid,$aid, $gid, $fid, $did, $eeid, $tid) {
        $update =DB::table('student')
         ->where('nid', $nid)
         ->update([
                'eid' => $eid,
                'mid' => $mid,
                'aid' => $aid,
                'gid' => $gid,
                'fid' => $fid,
                'did' => $did,
                'eeid' => $eeid,
                'tid' => $tid
         ]);
         if(strlen($update) > 0) 
          return response()->json(['status' => 'success'], 200);
         return response()->json(['status' => 'error'], 500);
     }
}
