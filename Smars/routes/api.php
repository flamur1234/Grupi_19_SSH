<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('students/{kf}', 'StudentController@getStudents');
Route::get('/', 'StudentController@test');

Route::post('student', 'StudentController@addStudent');
Route::put('student/{nid}/{kf}', 'StudentController@registerStudent');
Route::delete('deleteStudent/{nid}', 'StudentController@deleteStudent');
Route::put('updateStudent/{nid}/{eid}/{mid}/{aid}/{gid}/{fid}/{did}/{eeid}/{tid}', 'StudentController@updateStudent');
Route::get('admins/{firstName}/{password}', 'AdminController@getAdmin');
Route::post('registerAdmin', 'AdminController@addAdmin');
Route::delete('deleteAdmin/{adminId}', 'AdminController@deleteAdmin');
Route::put('updateAdmin/{adminId}/{firstName}/{lastName}/{email}/{password}', 'AdminController@updateAdmin');
Route::get('getAllAdmins', 'AdminController@getAllAdmins');
Route::get('admin/{first_name}/{pass}', 'AdminiKryesorController@getAdminKryesor');